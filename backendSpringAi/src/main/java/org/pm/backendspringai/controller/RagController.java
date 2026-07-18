package org.pm.backendspringai.controller;

import org.pm.backendspringai.service.RagService;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/chunks")
    public List<Document> documents() {
        return ragService.splitPdf();
    }
}
