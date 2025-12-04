package com.open.opendesk.DTO;

import com.open.opendesk.models.Authority;
import lombok.Data;

import java.util.Set;

@Data
public class RoleDTO {

    private String name;

    private String description;

    private Set<Long> authorityIdList;
}
