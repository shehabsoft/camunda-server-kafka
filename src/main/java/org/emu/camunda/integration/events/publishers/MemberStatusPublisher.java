package org.emu.camunda.integration.events.publishers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emu.common.dto.MemberApprovalDto;
import org.emu.common.dto.MemberDto;
import org.emu.common.dto.MemberStatus;
import org.emu.common.dto.NotifactionDto;
import org.emu.common.entities.Member;
import org.emu.common.events.MemberApprovalEvent;
import org.emu.common.events.MemberEvent;
import org.emu.common.events.MemberRejectionEvent;
import org.emu.common.events.NotificationEvent;
import org.emu.common.status.MemberApprovalStatus;
import org.emu.common.status.NotificationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class MemberStatusPublisher {

    private final Logger log = LoggerFactory.getLogger(MemberStatusPublisher.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Sinks.Many<MemberEvent> memberSupplier;
    public void raiseMemberEvent(final MemberDto member, MemberStatus memberStatus,String   processInstanceId ) {

        //
        var memberEvent = new MemberEvent(member, memberStatus, processInstanceId);
        memberEvent.setMessageName("MemberEvent");
        //
        log.error(memberEvent.toString());
        //
        this.memberSupplier.tryEmitNext(memberEvent);
    }

    @Autowired
    private Sinks.Many<MemberApprovalEvent> memberApprovalSink;
    public void raiseMemberApprovalEvent(final MemberDto member, MemberApprovalStatus memberStatus,String   processInstanceId  ){

        var memberEvent = new MemberApprovalEvent(member, memberStatus,processInstanceId);
        memberEvent.setMessageName("MemberApprovalEvent");

        this.memberApprovalSink.tryEmitNext(memberEvent);
    }

    @Autowired
    private Sinks.Many<MemberRejectionEvent> memberRejectionSink;
    public void raiseMemberRejectionSEvent(final MemberDto member, MemberApprovalStatus memberStatus,String   processInstanceId){

        var memberEvent = new MemberRejectionEvent(member, memberStatus,processInstanceId);
        memberEvent.setMessageName("MemberRejectionEvent");
        this.memberRejectionSink.tryEmitNext(memberEvent);
    }

}
