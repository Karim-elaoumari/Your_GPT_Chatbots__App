package com.chatbots.app.factory.seeders;

import com.chatbots.app.models.entities.Subscription;
import com.chatbots.app.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscriptionSeeder {
    private final SubscriptionRepository subscriptionRepository;
    public void seed(){
        List<Subscription> subscriptions = new ArrayList<>();
        Subscription subscription = Subscription.builder()
                .name("Free Subscription")
                .chatBotsLimit(2)
                .dataCountLimit(5)
                .initialDataCountLimit(5)
                .filesDataCharactersLimit(10000)
                .textDataCharactersLimit(5000)
                .QuestionsAndAnswersLimit(15)
                .userQuestionsLimitPerMonth(100)
                .build();
        subscriptions.add(subscription);
        Subscription subscription1 = Subscription.builder()
                .name("Premium Subscription")
                .chatBotsLimit(5)
                .dataCountLimit(20)
                .initialDataCountLimit(20)
                .filesDataCharactersLimit(20000)
                .textDataCharactersLimit(10000)
                .QuestionsAndAnswersLimit(40)
                .userQuestionsLimitPerMonth(1000)
                .build();
        subscriptions.add(subscription1);
        subscriptionRepository.saveAll(subscriptions);
    }
}
