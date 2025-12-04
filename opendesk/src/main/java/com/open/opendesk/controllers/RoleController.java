package com.open.opendesk.controllers;

import com.open.opendesk.DTO.RoleDTO;
import com.open.opendesk.models.Role;
import com.open.opendesk.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public Role createRole(@RequestBody RoleDTO roleDTO){
        return roleService.createRole(roleDTO);
    }
}
