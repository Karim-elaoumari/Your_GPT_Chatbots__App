package com.chatbots.app.services;

import com.chatbots.app.models.dto.ChatTextEmbeddingRequest;
import com.chatbots.app.models.entities.Embedding;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface EmbeddingService {

    List<Embedding> searchPlaces(UUID chatBotId,String prompt);
    List<Embedding> create(ChatTextEmbeddingRequest request);
    List<Embedding> createFromDocument(Integer ownerId,UUID chatBotId,MultipartFile file);
}
