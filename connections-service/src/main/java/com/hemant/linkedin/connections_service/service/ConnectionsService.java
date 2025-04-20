package com.hemant.linkedin.connections_service.service;


import com.hemant.linkedin.connections_service.auth.UserContextHolder;
import com.hemant.linkedin.connections_service.entity.Person;
import com.hemant.linkedin.connections_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnections(){

        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting FirstDegreeConnections id: {} ",userId);

        return personRepository.getFirstDegreeConnections(userId);
    }


}
