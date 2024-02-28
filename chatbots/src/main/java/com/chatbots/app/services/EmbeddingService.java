package com.chatbots.app.services;

import com.chatbots.app.models.entities.Embedding;

import java.util.List;

public interface EmbeddingService {

    List<Embedding> searchPlaces(String prompt);
    Embedding create(String text);
}
