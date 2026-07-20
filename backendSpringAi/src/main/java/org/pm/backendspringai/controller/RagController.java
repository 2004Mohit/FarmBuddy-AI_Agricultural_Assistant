package org.pm.backendspringai.controller;

import org.pm.backendspringai.dto.RagRequest;
import org.pm.backendspringai.dto.RagResponse;
import org.pm.backendspringai.rag.RagService;
import org.pm.backendspringai.service.VectorStoreService;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rag")
public class RagController {

//    private final RagService ragService;
//
//    public RagController(RagService ragService) {
//        this.ragService = ragService;
//    }
//
//    @GetMapping("/chunks")
//    public List<Document> documents() {
//        return ragService.splitPdf();
//    }

    private final VectorStoreService vectorStoreService;

    private final RagService ragService;

    public RagController(VectorStoreService vectorStoreService, RagService ragService) {
        this.vectorStoreService = vectorStoreService;
        this.ragService = ragService;
    }

    @GetMapping("/search")
    public List<Document> search(@RequestParam String query) {
        return vectorStoreService.search(query);
    }

    @PostMapping("/ask")
    public RagResponse ask(@RequestBody RagRequest request) {
        return ragService.ask(request);
    }
}