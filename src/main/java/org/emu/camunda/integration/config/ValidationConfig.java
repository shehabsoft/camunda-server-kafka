package org.emu.camunda.integration.config;


import org.emu.common.events.ValidationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class ValidationConfig {



    @Bean
    public Sinks.Many<ValidationEvent> validationSink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<ValidationEvent>> validationSupplier(Sinks.Many<ValidationEvent> sink){
        return sink::asFlux;
    }

}
