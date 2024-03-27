package com.chatbots.app.factory.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainSeeder implements CommandLineRunner {
    private final RoleSeeder roleSeeder;
    private final PrivilegeSeeder privilegeSeeder;
    private final ChatBotSeeder chatBotSeeder;
    private final SubscriptionSeeder subscriptionSeeder;
    @Override
    public void run(String... args) throws Exception {
//        privilegeSeeder.seed();
//        roleSeeder.seed();
//        subscriptionSeeder.seed();
//        chatBotSeeder.seed();
    }
}
