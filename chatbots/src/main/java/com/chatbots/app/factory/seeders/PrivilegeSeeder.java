package com.chatbots.app.factory.seeders;

import com.chatbots.app.models.entities.PrivilegeEntity;
import com.chatbots.app.models.enums.Privilege;
import com.chatbots.app.repositories.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PrivilegeSeeder {
    private final PrivilegeRepository privilegeRepository;
    public void seed() {
        List<PrivilegeEntity> privileges = new ArrayList<>();
        for (Privilege privilege : Privilege.values()) {
            privileges.add(PrivilegeEntity.builder().name(privilege).build());
        }
        privilegeRepository.saveAll(privileges);
    }

}
