package org.pm.backendspringai.rag;

import org.pm.backendspringai.service.VectorStoreService;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PdfLoader implements CommandLineRunner {

    private final VectorStoreService vectorStoreService;

    public PdfLoader(VectorStoreService vectorStoreService) {
        this.vectorStoreService = vectorStoreService;
    }

    @Override
    public void  run(String... args) {
        PagePdfDocumentReader reader = new PagePdfDocumentReader(
                new ClassPathResource("documents/SQL_Complete_Notes.pdf")
        );

        List<Document> documents = reader.read();

        TokenTextSplitter splitter = TokenTextSplitter.builder().build();

        List<Document> chunks = splitter.apply(documents);

        System.out.println("Original Documents : " + documents.size());
        System.out.println("Chunks : " + chunks.size());

        vectorStoreService.saveDocuments(chunks);

        System.out.println("Stored " + chunks.size() + " chunks successfully.");
    }
}
