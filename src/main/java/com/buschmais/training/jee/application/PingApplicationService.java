package com.buschmais.training.jee.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import static lombok.AccessLevel.PROTECTED;

@PrimaryPort
@ApplicationScoped
@Transactional
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Inject, access = PROTECTED)
@NoArgsConstructor(force = true, access = PROTECTED)
public class PingApplicationService {

    private final PingPublisher pingPublisher;

    public void publishPing(String pingMessage) {
        pingPublisher.ping(pingMessage);
    }

    public void consumePing(String pingMessage) {
        log.info("Ping '{}'.", pingMessage);
    }

}
