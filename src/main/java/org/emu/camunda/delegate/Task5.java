package org.emu.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * Copyright 2021-2022 By Dirac Systems.
 *
 * Created by khalid.nouh on 17/3/2021.
 */
@Component
public class Task5 implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("asigneeUser","kh");
        delegateExecution.setVariable("kh"," management");
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        runtimeService.setVariable(delegateExecution.getId(),"asignee2","demo");
        System.out.println("this is task1 as a java delegate");

    }
}
