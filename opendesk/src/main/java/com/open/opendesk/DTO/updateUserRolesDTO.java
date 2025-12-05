package com.open.opendesk.DTO;

import lombok.Getter;

import java.util.Set;

@Getter
public class updateUserRolesDTO {

    Set<Long> roleIds;

    Long userId;
}
