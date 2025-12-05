package com.open.opendesk.controllers;

import com.open.opendesk.DTO.*;
import com.open.opendesk.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/role/add")
    public ResponseEntity<UserDTO> updateUserRoles(@RequestBody updateUserRolesDTO updateUserRolesDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUserRoles(updateUserRolesDTO));
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(userDTO));
    }
}
