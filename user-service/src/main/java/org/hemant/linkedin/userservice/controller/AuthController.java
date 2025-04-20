package org.hemant.linkedin.userservice.controller;


import lombok.RequiredArgsConstructor;
import org.hemant.linkedin.userservice.DTO.LoginRequestDTO;
import org.hemant.linkedin.userservice.DTO.SignUpRequestDTO;
import org.hemant.linkedin.userservice.DTO.UserDTO;
import org.hemant.linkedin.userservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {

        UserDTO dto = authService.signUp(signUpRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        String token = authService.login(loginRequestDTO);
        return ResponseEntity.ok(token);



    }

}
