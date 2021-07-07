package org.emu.camunda.integration.events.publishers;

import org.emu.common.dto.ValidationDto;
import org.emu.common.events.ValidationEvent;
import org.emu.common.status.ValidationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class ValidationPublisher {

    @Autowired
    private Sinks.Many<ValidationEvent> documentSink;

    public void raiseValidationEvent(Object o, ValidationStatus documentStatus, String simpleClassName,String processInstanceId) {
           ValidationDto validationDto1=new ValidationDto();
        validationDto1.setGenericDto(o);
        validationDto1.setSimpleClassName(simpleClassName);
        var validationEvent = new ValidationEvent(validationDto1, documentStatus,processInstanceId);
        validationEvent.setTraceid("23234");
        validationEvent.setType("validation");
        validationEvent.setMessageName("ValidationEvent");
        this.documentSink.tryEmitNext(validationEvent);
    }
}
