package org.emu.camunda.delegate;



import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.emu.common.dto.MemberDto;
import org.emu.common.status.MemberApprovalStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
    public class MemberApprovalAdapter implements JavaDelegate {



    private final Logger log = LoggerFactory.getLogger(MemberApprovalAdapter.class);
    	  @Override
    	  public void execute(DelegateExecution context) throws Exception {

    		 Object approved =  context.getVariable("approved");
    		 log.error("Member Approved=" + approved);
    		 //
    		 Object id = (Object) context.getVariable("memberId");
    		 //
    		 log.error("Member Id=" + id);
              String idString = String.valueOf(id);
              Long convertedLong = Long.parseLong(idString);
    		 //
    		  int status  = ((Boolean)approved) ? 2 : 1;
              MemberDto memberDto=new MemberDto();
              memberDto.setId(convertedLong);

           // proxyFeign.raiseMemberApprovalEvent(memberDto, MemberApprovalStatus.APPROVED);

    	  }

}
