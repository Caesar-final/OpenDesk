package com.open.opendesk.services;

import com.open.opendesk.models.Authority;
import com.open.opendesk.repositories.AuthorityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepo authorityRepo;

    public List<Authority> getAllAuthorities(){
        return authorityRepo.findAll();
    }
}
