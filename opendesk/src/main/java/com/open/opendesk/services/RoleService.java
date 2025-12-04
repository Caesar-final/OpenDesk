package com.open.opendesk.services;

import com.open.opendesk.DTO.RoleDTO;
import com.open.opendesk.models.Authority;
import com.open.opendesk.models.Role;
import com.open.opendesk.repositories.AuthorityRepo;
import com.open.opendesk.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;

    private final AuthorityRepo authorityRepo;

    private final ModelMapper modelMapper;

    public Role createRole(RoleDTO roleDTO){
        Set<Authority> authoritySet = roleDTO.getAuthorityIdList()
                .stream()
                .map(authorityRepo::findById)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());

        Role roleToCreate = new Role();

        roleToCreate.setAuthorities(authoritySet);
        roleToCreate.setName(roleDTO.getName());
        roleToCreate.setDescription(roleDTO.getDescription());

        return roleRepo.save(roleToCreate);
    }
}
