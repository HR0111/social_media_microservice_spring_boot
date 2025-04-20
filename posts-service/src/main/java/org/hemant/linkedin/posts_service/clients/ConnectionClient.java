package org.hemant.linkedin.posts_service.clients;


import org.hemant.linkedin.posts_service.DTO.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "connections-service" , path = "/connections")
public interface ConnectionClient {

//
//    @GetMapping("/core/{userId}/first-degree")
//     List<PersonDTO> getFirstDegreeConnections(@PathVariable Long userId);

    @GetMapping("/core/first-degree")
     List<PersonDTO> getFirstDegreeConnections();





}
