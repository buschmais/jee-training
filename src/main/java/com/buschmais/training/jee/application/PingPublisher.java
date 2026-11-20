package com.buschmais.training.jee.application;

import org.jmolecules.architecture.hexagonal.SecondaryPort;

@SecondaryPort
public interface PingPublisher {

    void ping(String message);

}
