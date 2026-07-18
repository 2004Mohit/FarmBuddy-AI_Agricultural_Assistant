package org.pm.backendspringai.service;

import org.jspecify.annotations.Nullable;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;

    public EmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    private EmbeddingResponse generateEmbedding(String text) {
        return embeddingModel.embedForResponse(List.of(text));
    }

    public EmbeddingResponse generateEmbedding(Document document) {
        return generateEmbedding(document.getText());
    }

}
