package com.chatbots.app.factory.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainSeeder implements CommandLineRunner {
    private final RoleSeeder roleSeeder;
    private final PrivilegeSeeder privilegeSeeder;
    private final ChatBotSeeder chatBotSeeder;
    @Override
    public void run(String... args) throws Exception {
//        privilegeSeeder.seed();
//        roleSeeder.seed();
//        chatBotSeeder.seed();
    }
}
