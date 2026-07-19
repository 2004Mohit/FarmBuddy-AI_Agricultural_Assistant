package org.pm.backendspringai.controller;

import org.pm.backendspringai.rag.RagService;
import org.pm.backendspringai.service.VectorStoreService;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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

    public RagController(VectorStoreService vectorStoreService) {
        this.vectorStoreService = vectorStoreService;
    }

    @GetMapping("/search")
    public List<Document> search(@RequestParam String query) {
        return vectorStoreService.search(query);
    }
}
