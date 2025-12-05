package com.open.opendesk.config;

import com.open.opendesk.DTO.UserDTO;
import com.open.opendesk.models.Role;
import com.open.opendesk.models.User;
import com.open.opendesk.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {

    private final RoleRepo roleRepo;

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        TypeMap<UserDTO, User> userDTOUserTypeMap = modelMapper.createTypeMap(UserDTO.class, User.class);

        userDTOUserTypeMap.addMappings(mapper -> {
            mapper.map(UserDTO::getId,User::setId);
            mapper.map(UserDTO::getUsername, User::setUsername);
            mapper.map(UserDTO::getEmail, User::setEmail);
            mapper.map(UserDTO::isEnabled, User::setEnabled);
            mapper.map(UserDTO::isAccountNonExpired, User::setAccountNonExpired);
            mapper.map(UserDTO::isCredentialsNonExpired, User::setCredentialsNonExpired);
            mapper.map(UserDTO::isAccountNonLocked, User::setAccountNonLocked);
        });

        userDTOUserTypeMap.addMappings(mapper ->
                mapper.using(ctx -> {
                    @SuppressWarnings("unchecked")
                    Set<Long> roleIds = (Set<Long>) ctx.getSource();
                    if (roleIds == null || roleIds.isEmpty()) {
                        return Set.of();
                    }
                    return roleIds.stream()
                            .map(roleRepo::findById)
                            .flatMap(Optional::stream)
                            .collect(Collectors.toSet());
                }).map(UserDTO::getRoleIds, User::setRoles)
        );

        TypeMap<User, UserDTO> userEntityToDTOMap = modelMapper.createTypeMap(User.class, UserDTO.class);

        userEntityToDTOMap.addMappings(mapper ->
                mapper.using(ctx -> {
                    @SuppressWarnings("unchecked")
                    Set<Role> roles = (Set<Role>) ctx.getSource();
                    if (roles == null) return Set.of();
                    return roles.stream()
                            .map(Role::getId)
                            .collect(Collectors.toSet());
                }).map(User::getRoles, UserDTO::setRoleIds)
        );
        return modelMapper;
    }
}
