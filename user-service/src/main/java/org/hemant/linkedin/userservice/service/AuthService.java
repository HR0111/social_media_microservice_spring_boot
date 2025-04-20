package org.hemant.linkedin.userservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hemant.linkedin.userservice.DTO.LoginRequestDTO;
import org.hemant.linkedin.userservice.DTO.SignUpRequestDTO;
import org.hemant.linkedin.userservice.DTO.UserDTO;
import org.hemant.linkedin.userservice.entity.User;
import org.hemant.linkedin.userservice.exception.BadRequestException;
import org.hemant.linkedin.userservice.exception.ResourceNotFoundException;
import org.hemant.linkedin.userservice.repository.UserRepository;
import org.hemant.linkedin.userservice.utils.PasswordUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    public UserDTO signUp(SignUpRequestDTO signUpRequestDTO) {

        User user = modelMapper.map(signUpRequestDTO, User.class);
        user.setPassword(PasswordUtil.hashPassword(signUpRequestDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);

    }

    public String login(LoginRequestDTO loginRequestDTO) {

        User user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() ->
                new ResourceNotFoundException("User with email " + loginRequestDTO.getEmail() + " not found"));

        boolean isPasswordMatch = PasswordUtil.checkPassword(loginRequestDTO.getPassword(), user.getPassword());

        if(!isPasswordMatch) {
            throw new BadRequestException("Incorrect password");
        }

        return jwtService.generateAccessToken(user);

    }
}
