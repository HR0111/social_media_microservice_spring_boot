package org.hemant.linkedin.notificationsservice.clients;


import org.hemant.linkedin.notificationsservice.DTO.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "connections-service" , path = "/connections")
public interface ConnectionClient {


    @GetMapping("/core/first-degree")
     List<PersonDTO> getFirstDegreeConnections(@RequestHeader("X-User-Id") Long userId);




}
