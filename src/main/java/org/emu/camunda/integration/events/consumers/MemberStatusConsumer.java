package org.emu.camunda.integration.events.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emu.camunda.utilities.CamundaUtils;
import org.emu.common.dto.MemberApprovalDto;
import org.emu.common.dto.MemberDto;
import org.emu.common.events.MemberApprovalEvent;
import org.emu.common.events.MemberEvent;
import org.emu.common.events.MemberRejectionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MemberStatusConsumer {

    private final Logger log = LoggerFactory.getLogger(MemberStatusConsumer.class);

@Autowired
CamundaUtils camundaUtils;
    @Autowired
    private ObjectMapper objectMapper;

//    @Bean
//    public Consumer<MemberEvent> memberEventConsumer() {
//
//        return me -> {
//            log.debug("Inside memberEventConsumer");
//            if (me.getStatus() != null && me.getStatus().toString().equals("CREATED")) {
//                MemberDto memberDto = objectMapper.convertValue(me.getGenericDto(), MemberDto.class);
//                System.out.println(memberDto);
//            } else
//              if (me.getStatus() != null && me.getStatus().toString().equals("REJECTED")) {
//                MemberDto memberDto = objectMapper.convertValue(me.getGenericDto(), MemberDto.class);
//
//            }
//              camundaUtils.startProcessByMessageEvent(me);
//
//        };
//    }

    @Bean
    public Consumer<MemberApprovalEvent> memberApprovalEventConsumer() {
        return me -> {
                log.debug("Inside MemberApprovalEvent");
                if (me.getStatus() != null && me.getStatus().toString().equals("APPROVED")) {
                    MemberDto memberDto = objectMapper.convertValue(me.getGenericDto(), MemberDto.class);
                    log.debug("Approved Member :"+memberDto);
                    System.out.println(memberDto);
                    System.out.println(memberDto.getAddress2());
                    camundaUtils.startProcessByMessageEvent(me);
                }
        };
    }

    @Bean
        public Consumer<MemberRejectionEvent> memberRejectionEventConsumer() {
        return me -> {
            log.debug("Inside MemberRejectionEvent");
            if (me.getStatus() != null && me.getStatus().toString().equals("REJECTED")) {
                MemberDto memberDto = objectMapper.convertValue(me.getGenericDto(), MemberDto.class);
                log.debug("Approved Member :"+memberDto);
                System.out.println(memberDto);
                System.out.println(memberDto.getAddress2());
                camundaUtils.startProcessByMessageEvent(me);
            }
        };
    }
}
