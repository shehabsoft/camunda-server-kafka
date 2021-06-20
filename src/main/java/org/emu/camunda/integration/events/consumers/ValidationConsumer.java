package org.emu.camunda.integration.events.consumers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.emu.camunda.integration.events.publishers.MemberStatusPublisher;
import org.emu.camunda.utilities.CamundaUtils;
import org.emu.common.dto.MemberDto;
import org.emu.common.dto.ValidationDto;
import org.emu.common.events.ValidationEvent;
import org.emu.common.status.MemberApprovalStatus;
import org.emu.common.status.ValidationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
public class ValidationConsumer {

    @Autowired
    MemberStatusPublisher memberStatusPublisher;


    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CamundaUtils camundaUtils;
    @Bean
    public Consumer<ValidationEvent> validationEventConsumer() {
        return ie -> {

            if (!ie.getStatus().equals("NEW")) {

                ValidationDto validationDto = objectMapper.convertValue(ie.getGenericDto(), ValidationDto.class);
               // camundaUtils.startProcessByMessageEvent(ie);


                Map genericDto = (Map) validationDto.getGenericDto();
                LinkedHashMap map= (LinkedHashMap) genericDto.get("genericDto");
                Gson gson = new Gson();

                String json = gson.toJson(map, LinkedHashMap.class);
                MemberDto memberDto = gson.fromJson(json, MemberDto.class);

                System.out.println(memberDto);
                if (ie.getStatus().equals("VALIDATED")) {

                    memberStatusPublisher.raiseMemberApprovalEvent(memberDto, MemberApprovalStatus.APPROVED, ie.getProcessInstanceId());

                } else {
                    memberStatusPublisher.raiseMemberRejectionSEvent(memberDto, MemberApprovalStatus.REJECTED, ie.getProcessInstanceId());

                }
            }



        };
    }
}
