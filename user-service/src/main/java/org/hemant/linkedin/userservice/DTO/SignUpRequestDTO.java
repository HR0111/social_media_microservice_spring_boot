package org.hemant.linkedin.userservice.DTO;


import lombok.Data;

@Data
public class SignUpRequestDTO {

    private String email;
    private String password;
    private String name;

    

}
