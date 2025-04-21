package org.hemant.linkedin.notificationsservice.repository;

import org.hemant.linkedin.notificationsservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {



}
