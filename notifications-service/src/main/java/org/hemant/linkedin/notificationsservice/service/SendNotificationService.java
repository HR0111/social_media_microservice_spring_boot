package org.hemant.linkedin.notificationsservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hemant.linkedin.notificationsservice.entity.Notification;
import org.hemant.linkedin.notificationsservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendNotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(Long userId , String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUserId(userId);

        notificationRepository.save(notification);
        log.info("Notification sent to user with id : %id",userId);
    }
}
