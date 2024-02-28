package com.chatbots.app.factory.fakers;


import com.chatbots.app.models.entities.PrivilegeEntity;
import com.chatbots.app.models.entities.Role;
import com.chatbots.app.models.enums.UserRole;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RoleFaker {
    private final Faker faker = new Faker();

    public Role makeRole(List<PrivilegeEntity> privileges, UserRole name) {
        return Role.builder()
                .name(name)
                .privileges(
                      privileges
                )
                .build();
    }


}
