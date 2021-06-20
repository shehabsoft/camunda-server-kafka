package org.emu.camunda.integration.config;

import org.emu.common.events.MemberApprovalEvent;
import org.emu.common.events.MemberEvent;
import org.emu.common.events.MemberRejectionEvent;
import org.emu.common.events.NotificationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class MembershipConfig {
    @Bean
    public Sinks.Many<MemberApprovalEvent> memberApprovalSink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }
    @Bean
    public Supplier<Flux<MemberApprovalEvent>> memberApprovalSupplier(Sinks.Many<MemberApprovalEvent> sink){
        return sink::asFlux;
    }
    @Bean
    public Sinks.Many<MemberEvent> memberSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }
    @Bean
    public Supplier<Flux<MemberEvent>> memberSupplier(Sinks.Many<MemberEvent> sink) {
        return sink::asFlux;
    }

    @Bean
    public Sinks.Many<MemberRejectionEvent> memberRejectionSink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }
    @Bean
    public Supplier<Flux<MemberRejectionEvent>> memberRejectionSupplier(Sinks.Many<MemberRejectionEvent> sink){
        return sink::asFlux;
    }

}
