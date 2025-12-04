package com.open.opendesk.DTO;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private String email;

    private boolean enabled;

    private boolean accountNonExpired;

    private boolean credentialsNonExpired;

    private boolean accountNonLocked;

    private Set<Long> roleIds;

    private Date createdAt;

    private Date updatedAt;
}
