package org.hemant.linkedin.notificationsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NotificationsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationsServiceApplication.class, args);
    }

}
