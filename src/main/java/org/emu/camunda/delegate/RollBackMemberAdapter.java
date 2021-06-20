package org.emu.camunda.delegate;



import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.emu.common.dto.MemberDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
    public class RollBackMemberAdapter implements JavaDelegate {



    private final Logger log = LoggerFactory.getLogger(RollBackMemberAdapter.class);
    	  @Override
    	  public void execute(DelegateExecution context) throws Exception {


    		 //
    		 Object id = (Object) context.getVariable("memberId");
    		 //
    		 log.error("Member Id=" + id);
              String idString = String.valueOf(id);
              Long convertedLong = Long.parseLong(idString);
    		 //

              MemberDto memberDto=new MemberDto();
              memberDto.setId(convertedLong);
            //status 1  for deletion
        //    proxyFeign.raiseMemberEvent(memberDto, MemberStatus.REJECTED);

    	  }

}
