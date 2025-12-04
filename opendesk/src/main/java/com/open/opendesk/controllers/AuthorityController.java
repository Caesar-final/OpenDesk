package com.open.opendesk.controllers;

import com.open.opendesk.models.Authority;
import com.open.opendesk.services.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping
    public List<Authority> getAllAuthorities(){
        return authorityService.getAllAuthorities();
    }


}
