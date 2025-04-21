package com.hemant.linkedin.connections_service.service;


import com.hemant.linkedin.connections_service.auth.UserContextHolder;
import com.hemant.linkedin.connections_service.entity.Person;
import com.hemant.linkedin.connections_service.event.AcceptConnectionRequestEvent;
import com.hemant.linkedin.connections_service.event.SendConnectionRequestEvent;
import com.hemant.linkedin.connections_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsService {

    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, SendConnectionRequestEvent> sendConnectionRequestEventKafkaTemplate;
    private final KafkaTemplate<Long, AcceptConnectionRequestEvent> acceptConnectionRequestEventKafkaTemplate;

    public List<Person> getFirstDegreeConnections() {

        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting FirstDegreeConnections id: {} ", userId);

        return personRepository.getFirstDegreeConnections(userId);
    }


    public Boolean sendConnectionRequest(Long receiverId) {

        Long senderId = UserContextHolder.getCurrentUserId();
        log.info("Sending connection request Sender : {} , receiver : {} ", senderId, receiverId);

        if (senderId.equals(receiverId)) {
            throw new RuntimeException("Sender and Receiver are the same");
        }

        boolean alreadySentReuqest = personRepository.connectionRequestExists(senderId, receiverId);

        if (alreadySentReuqest) {
            throw new RuntimeException("Connection request already exists , cannot send connection request AGAIN");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);


        if (alreadyConnected) {
            throw new RuntimeException("Already Connected User, cannot send connection request AGAIN");
        }


        log.info("Successfully Sent connection request");
        personRepository.addConnectionRequest(senderId, receiverId);

        SendConnectionRequestEvent sendConnectionRequestEvent = SendConnectionRequestEvent.builder()
                .senderId(senderId).receiverId(receiverId)
                .build();

        sendConnectionRequestEventKafkaTemplate.send("send-connection-request-topic", sendConnectionRequestEvent);
        return true;

    }

    public Boolean acceptConnectionRequest(Long senderId) {

        Long receiverId = UserContextHolder.getCurrentUserId();

        boolean connectionRequestExists = personRepository.connectionRequestExists(senderId, receiverId);
        if (!connectionRequestExists) {
            throw new RuntimeException(" Connection request does not exist to Accept");
        }

        personRepository.acceptConnectionRequest(senderId, receiverId);
        log.info("Successfully Accepted connection request , senderId {} , receiverId {}", senderId, receiverId);
        AcceptConnectionRequestEvent acceptConnectionRequestEvent = AcceptConnectionRequestEvent.builder()
                .senderId(senderId).receiverId(receiverId)
                .build();

        acceptConnectionRequestEventKafkaTemplate.send("accept-connection-request-topic", acceptConnectionRequestEvent);
        return true;

    }

    public Boolean rejectConnectionRequest(Long senderId) {

        Long receiverId = UserContextHolder.getCurrentUserId();
        boolean connectionRequestExists = personRepository.connectionRequestExists(senderId, receiverId);
        if (!connectionRequestExists) {
            throw new RuntimeException(" Connection request does not exist to Reject or DELETE");
        }

        personRepository.rejectConnectionRequest(senderId, receiverId);
        return true;

    }
}
