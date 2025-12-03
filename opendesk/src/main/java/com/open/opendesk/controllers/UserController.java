package com.open.opendesk.controllers;

import com.open.opendesk.DTO.LoginRequestDTO;
import com.open.opendesk.DTO.LoginResponseDTO;
import com.open.opendesk.DTO.UserDTO;
import com.open.opendesk.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Value("${jwt.header}")
    private String jwtHeader;

    private final UserService userService;

    @PostMapping("/register")
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){

        String jwt = userService.loginUser(loginRequestDTO);

        return  ResponseEntity.status(HttpStatus.OK).header(jwtHeader,jwt)
                .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(),jwt));
    }
}
