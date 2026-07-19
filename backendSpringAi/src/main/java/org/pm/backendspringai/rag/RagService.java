package org.pm.backendspringai.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagService {

    public List<Document> splitPdf() {
        PagePdfDocumentReader reader = new PagePdfDocumentReader(
                new ClassPathResource("documents/SQL_Complete_Notes.pdf")
        );

        List<Document> documents = reader.read();

        TokenTextSplitter splitter = TokenTextSplitter.builder().build();

        List<Document> chunks = splitter.apply(documents);

        System.out.println("Original Documents: " + documents.size());
        System.out.println("Chunks: " + chunks.size());

        return chunks;
    }
}
