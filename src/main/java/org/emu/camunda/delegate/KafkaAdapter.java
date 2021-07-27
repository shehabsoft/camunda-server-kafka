package org.emu.camunda.delegate;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.core.variable.CoreVariableInstance;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.emu.camunda.integration.events.publishers.MemberStatusPublisher;
import org.emu.camunda.integration.events.publishers.ValidationPublisher;
import org.emu.common.dto.MemberDto;
import org.emu.common.dto.MemberStatus;
import org.emu.common.dto.bpm.EVENTSTYPES;
import org.emu.common.events.ValidationEvent;
import org.emu.common.status.MemberApprovalStatus;
import org.emu.common.status.ValidationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


@Component
public class KafkaAdapter implements JavaDelegate {

@Autowired
    MemberStatusPublisher memberStatusPublisher;

    @Autowired
    ValidationPublisher validationPublisher;
    @Autowired
    private ObjectMapper objectMapper;
	  private final Logger log = LoggerFactory.getLogger(KafkaAdapter.class);

	  @Override
	  public void execute(DelegateExecution context) throws Exception {
          String processInstanceId = context.getProcessInstanceId();
          ExecutionEntity executionCasted = ((ExecutionEntity) context);
          List<CoreVariableInstance> vars = executionCasted.getVariableInstancesLocal();
          Map<String, Object> varsMap = new HashMap<>();
          for (CoreVariableInstance v : vars) {
              varsMap.put(v.getName(), v.getTypedValue(true).getValue());
          }
          //
          String traceId = context.getProcessBusinessKey();
          String event = (String) varsMap.get("event");
          LinkedHashMap payload = (LinkedHashMap) varsMap.get("payload");
          MemberDto memberDto=null;
          Gson gson = new Gson();
          try {
              Object payloadObject = payload.get(0);
              memberDto = objectMapper.convertValue(payload, MemberDto.class);
              context.setVariable("memberId",memberDto.getId());
              context.setVariable("firstName",memberDto.getFirstName());
              context.setVariable("lastName",memberDto.getLastName());

              //  MemberDto memberDto = gson.fromJson(payloadObject, MemberDto.class);
              LinkedHashMap approved=null;
              boolean status;
              switch(event)
              {
                  case EVENTSTYPES.MEMBER_EVENT:

                        approved = (LinkedHashMap)context.getVariable("approved");
                        String approvedV=(String)approved.get("value");

                      if(approvedV.equalsIgnoreCase("true")) {
                          memberStatusPublisher.raiseMemberEvent(memberDto, MemberStatus.COMPLETED, processInstanceId);
                      }else
                      {
                          memberStatusPublisher.raiseMemberEvent(memberDto, MemberStatus.REJECTED, processInstanceId);
                      }
                      break;
//                  case EVENTSTYPES.VALIDATED_EVENT:
//                        approved =  context.getVariable("approved");
//
//                        status  = ((Boolean)approved) ;
//                      if(status) {
//                          validationPublisher.raiseValidationEvent(memberDto, ValidationStatus.NEW,"Member",processInstanceId);
//
//                      }else
//                      {
//                          memberStatusPublisher.raiseMemberRejectionSEvent(memberDto, MemberApprovalStatus.REJECTED,processInstanceId);
//                      }
//                      break;
             }
          } catch (Exception e)
          {
              e.printStackTrace();
          }


	  }

	}
