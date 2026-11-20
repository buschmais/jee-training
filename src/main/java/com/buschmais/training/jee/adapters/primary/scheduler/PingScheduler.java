package com.buschmais.training.jee.adapters.primary.scheduler;

import com.buschmais.training.jee.application.PingApplicationService;

import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static jakarta.ejb.TransactionAttributeType.NEVER;
import static lombok.AccessLevel.PROTECTED;

@Stateless
@TransactionAttribute(NEVER)
@RequiredArgsConstructor(onConstructor_ = @Inject, access = PROTECTED)
@NoArgsConstructor(force = true, access = PROTECTED)
public class PingScheduler {

    private final PingApplicationService pingApplicationService;

    @Schedule(hour = "*", minute = "*", second = "*")
    public void schedule() {
        pingApplicationService.publishPing("Hello World!");
    }

}
