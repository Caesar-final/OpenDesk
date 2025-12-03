package com.open.opendesk.services;

import com.open.opendesk.DTO.LoginRequestDTO;
import com.open.opendesk.DTO.LoginResponseDTO;
import com.open.opendesk.DTO.UserDTO;
import com.open.opendesk.models.User;
import com.open.opendesk.repositories.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.secret.default.value}")
    private String jwtSecretDefaultValue;

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final Environment environment;

    private final AuthenticationManager authenticationManager;

    public UserDTO createUser(UserDTO userDTO){

        String hashPwd = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(hashPwd);
        userDTO.setCreatedAt(new Date(System.currentTimeMillis()));

        User userData = modelMapper.map(userDTO, User.class);

        User createdUser = userRepo.save(userData);

        return modelMapper.map(createdUser, UserDTO.class);
    }

    public UserDTO getUserById(Long id){

        Optional<User> user = userRepo.findById(id);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("User with id " + id + " not found");
        }

        return  modelMapper.map(user, UserDTO.class);
    }

    public String loginUser(LoginRequestDTO loginRequestDTO) {

        String jwt = "";

        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDTO.username(),loginRequestDTO.password());

        Authentication authenticationResponse =  authenticationManager.authenticate(authentication);

        if(null != authenticationResponse && authenticationResponse.isAuthenticated()){
            if(null != environment){
                String secret = jwtSecret != null ? jwtSecret : jwtSecretDefaultValue;
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt = Jwts.builder()
                        .issuer("OpenDesk")
                        .subject("JWT Token")
                        .claim("username",authenticationResponse.getName())
                        .claim("authorities",authenticationResponse.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority
                        ).collect(Collectors.joining(",")))
                        .issuedAt(new java.util.Date())
                        .expiration(new java.util.Date(new java.util.Date().getTime() + 30000000))
                        .signWith(secretKey)
                        .compact();
            }
        }
        return jwt;
    }
}
