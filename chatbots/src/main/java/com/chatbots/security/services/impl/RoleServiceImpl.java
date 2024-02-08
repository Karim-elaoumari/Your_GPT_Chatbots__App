package com.chatbots.security.services.impl;

import com.chatbots.security.models.entities.Role;
import com.chatbots.security.models.enums.UserRole;
import com.chatbots.security.repositories.RoleRepository;
import com.chatbots.security.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public Role getRoleByName(UserRole name) {
        return roleRepository.findByName(name).orElseThrow(()-> new RuntimeException("Role not found"));
    }
}
