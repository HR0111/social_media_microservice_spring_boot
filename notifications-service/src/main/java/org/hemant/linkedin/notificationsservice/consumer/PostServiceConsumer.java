package org.hemant.linkedin.notificationsservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hemant.linkedin.notificationsservice.DTO.PersonDTO;
import org.hemant.linkedin.notificationsservice.clients.ConnectionClient;
import org.hemant.linkedin.notificationsservice.entity.Notification;
import org.hemant.linkedin.notificationsservice.repository.NotificationRepository;
import org.hemant.linkedin.notificationsservice.service.SendNotificationService;
import org.hemant.linkedin.posts_service.event.PostCreatedEvent;
import org.hemant.linkedin.posts_service.event.PostLikedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceConsumer {

    private final ConnectionClient connectionClient;
    private final SendNotificationService sendNotificationService;

    @KafkaListener(topics = "post-created-topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent) {

        log.info("Sending notification handlePostCreated : ");

        List<PersonDTO> connections = connectionClient.getFirstDegreeConnections(postCreatedEvent.getCreatorId());

        for(PersonDTO connection : connections) {
            sendNotificationService.sendNotification(connection.getUserId() , "Your Connection "+ postCreatedEvent.getCreatorId() + " has been created a post Check it OUT"  );
        }
    }

    @KafkaListener(topics = "post-liked-topic")
    public void handlePostLiked(PostLikedEvent postLikedEvent) {

        log.info("Sending notification handlePostLiked : {}", postLikedEvent);

        String message = String.format("Your Post  , %d has been liked by %d",postLikedEvent.getPostId() , postLikedEvent.getLikedByUserId());

        sendNotificationService.sendNotification(postLikedEvent.getCreatorId() , message);
    }


}
