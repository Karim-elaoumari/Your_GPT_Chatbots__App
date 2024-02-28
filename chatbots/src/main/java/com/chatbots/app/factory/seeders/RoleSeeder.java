package com.chatbots.app.factory.seeders;

import com.chatbots.app.models.entities.PrivilegeEntity;
import com.chatbots.app.models.entities.Role;
import com.chatbots.app.models.enums.UserRole;
import com.chatbots.app.repositories.PrivilegeRepository;
import com.chatbots.app.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleSeeder {
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final com.chatbots.app.factory.fakers.RoleFaker RoleFaker;

    public void seed() {
        List<PrivilegeEntity> privileges = privilegeRepository.findAll();
        privileges.remove(4);
        Role role = RoleFaker.makeRole(privileges, UserRole.ADMIN);
        roleRepository.save(role);
        Role role1 = RoleFaker.makeRole(privileges,UserRole.USER);
        roleRepository.save(role1);
    }

}
