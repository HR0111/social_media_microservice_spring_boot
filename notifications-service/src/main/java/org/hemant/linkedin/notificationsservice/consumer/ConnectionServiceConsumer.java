package org.hemant.linkedin.notificationsservice.consumer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hemant.linkedin.connections_service.event.AcceptConnectionRequestEvent;
import org.hemant.linkedin.connections_service.event.SendConnectionRequestEvent;
import org.hemant.linkedin.notificationsservice.service.SendNotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionServiceConsumer {

    private final SendNotificationService sendNotificationService;

    @KafkaListener(topics = "send-connection-request-topic")
    public void handleSendConnectionRequest(SendConnectionRequestEvent sendConnectionRequestEvent){

        String message = "You have received a new connection request with id : %id"+sendConnectionRequestEvent.getSenderId();
        sendNotificationService.sendNotification(sendConnectionRequestEvent.getReceiverId(), message);

    }

    @KafkaListener(topics = "accept-connection-request-topic")
    public void handleAcceptConnectionRequest(AcceptConnectionRequestEvent acceptConnectionRequestEvent){

        String message = "Your Connection request has been accepted by the user  with id : %id"+acceptConnectionRequestEvent.getReceiverId();
        sendNotificationService.sendNotification(acceptConnectionRequestEvent.getSenderId(), message);

    }

}
