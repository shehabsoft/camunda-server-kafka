package org.emu.camunda.delegate;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.emu.common.dto.MemberDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
    public class MemberValidationAdapter implements JavaDelegate {




    private final Logger log = LoggerFactory.getLogger(MemberValidationAdapter.class);
    	  @Override
    	  public void execute(DelegateExecution context) throws Exception {

    		 MemberDto memberDto = (MemberDto) context.getVariable("memberDto");


    		 log.error("Member Approved=" + memberDto);


//             proxyFeign.raiseValidationEvent(memberDto);

    	  }

}
