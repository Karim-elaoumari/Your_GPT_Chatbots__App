package com.chatbots.app.services;

import com.chatbots.app.models.dto.ChatTextEmbeddingRequest;
import com.chatbots.app.models.entities.Data;
import com.chatbots.app.models.entities.Embedding;
import com.chatbots.app.models.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface EmbeddingService {

    List<Embedding> searchPlaces(UUID chatBotId,String prompt);
    Data create(ChatTextEmbeddingRequest request, User user);
    Data createFromDocument(Integer ownerId,UUID chatBotId,MultipartFile file);

}
