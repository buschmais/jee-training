package com.buschmais.training.jee.adapters.secondary.jms;

import com.buschmais.training.jee.application.PingPublisher;
import com.buschmais.training.jee.shared.jms.Ping;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

@ApplicationScoped
public class PingPublisherImpl implements PingPublisher {

    private final Queue pingQueue;

    private final JMSContext jmsContext;

    @Inject
    PingPublisherImpl(@Ping Queue pingQueue, JMSContext jmsContext) {
        this.pingQueue = pingQueue;
        this.jmsContext = jmsContext;
    }

    @Override
    public void ping(String message) {
        jmsContext.createProducer()
                .send(pingQueue, message);
    }
}
