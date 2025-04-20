package com.hemant.linkedin.connections_service.controller;


import com.hemant.linkedin.connections_service.entity.Person;
import com.hemant.linkedin.connections_service.repository.PersonRepository;
import com.hemant.linkedin.connections_service.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class ConnectionsController {


    private final ConnectionsService connectionsService;


    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections(){

        return ResponseEntity.ok(connectionsService.getFirstDegreeConnections());
    }



}
