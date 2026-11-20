package com.buschmais.training.jee.adapters.primary.jms;

import com.buschmais.training.jee.application.PingApplicationService;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJBException;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static com.buschmais.training.jee.shared.jms.JMSConfiguration.PING_QUEUE;

@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = PING_QUEUE) }, mappedName = PING_QUEUE)
@RequiredArgsConstructor(onConstructor_ = @Inject)
@NoArgsConstructor(force = true)
public class PingListener implements MessageListener {

    /**
     * Verwendung von Constructor-Injection, sofern durch den verwendeten Container (z.B. WildFly) unterstützt.
     */
    private final PingApplicationService pingApplicationService;

    @Override
    public void onMessage(Message message) {
        String pingMessage = getPingMessage(message);
        pingApplicationService.consumePing(pingMessage);
    }

    private static String getPingMessage(Message message) {
        try {
            return message.getBody(String.class);
        } catch (JMSException e) {
            throw new EJBException("Cannot consume ping message.", e);
        }
    }
}
