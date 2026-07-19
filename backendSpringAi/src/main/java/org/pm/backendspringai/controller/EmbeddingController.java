package org.pm.backendspringai.controller;

import org.pm.backendspringai.service.EmbeddingService;
import org.pm.backendspringai.rag.RagService;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmbeddingController {

    private final RagService ragService;
    private final EmbeddingService embeddingService;

    public EmbeddingController(RagService ragService,
                               EmbeddingService embeddingService) {
        this.ragService = ragService;
        this.embeddingService = embeddingService;
    }

    @GetMapping("/embedding")
    public String embedding() {

        List<Document> chunks = ragService.splitPdf();

        EmbeddingResponse response =
                embeddingService.generateEmbedding(chunks.getFirst());

        return "Generated " +
                response.getResults().size() +
                " embedding(s)";
    }
}