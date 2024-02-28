package com.chatbots.app.services.impl;

import com.chatbots.app.models.entities.Role;
import com.chatbots.app.models.enums.UserRole;
import com.chatbots.app.repositories.RoleRepository;
import com.chatbots.app.services.RoleService;
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
