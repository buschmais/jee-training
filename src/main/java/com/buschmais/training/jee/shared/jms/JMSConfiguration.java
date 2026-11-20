package com.buschmais.training.jee.shared.jms;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

@ApplicationScoped
public class JMSConfiguration {

    public static final String PING_QUEUE = "jms/queue/Ping";

    public static final String PONG_QUEUE = "jms/queue/Pong";

    @Produces
    @Ping
    @Resource(mappedName = "java:/" + PING_QUEUE)
    private Queue pingQueue;

    @Produces
    @Pong
    @Resource(mappedName = "java:/" + PONG_QUEUE)
    private Queue pongQueue;

 //   @Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext nonXAJMSContext;

    @Produces
    @NonXA
    JMSContext getNonXAJMSContext() {
        return nonXAJMSContext;
    }
}
