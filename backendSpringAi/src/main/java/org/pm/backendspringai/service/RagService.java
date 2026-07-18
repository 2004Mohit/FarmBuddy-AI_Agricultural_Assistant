package org.pm.backendspringai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagService {

    public List<Document> readPdf() {
        PagePdfDocumentReader reader = new PagePdfDocumentReader(
                new ClassPathResource("documents/2025_Arch_CutOff.pdf")
        );
        return reader.read();
    }
}
