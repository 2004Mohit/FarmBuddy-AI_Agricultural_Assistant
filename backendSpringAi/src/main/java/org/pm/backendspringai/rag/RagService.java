package org.pm.backendspringai.rag;

import org.pm.backendspringai.dto.RagRequest;
import org.pm.backendspringai.dto.RagResponse;
import org.pm.backendspringai.service.VectorStoreService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public RagService(@Qualifier("ragChatClient") ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    public RagResponse ask(RagRequest request) {
        String answer = chatClient.prompt()
                .advisors(
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(
                                        SearchRequest.builder()
                                                .topK(5)
                                                .similarityThreshold(0.4)
                                                .build()
                                )
                                .build()
                )
                .user(request.question())
                .call()
                .content();

        return new RagResponse(answer);
    }
}

//    ------------------MANUAL RAG IMPLEMENTATION CODE------------------------

//    private final VectorStoreService vectorStoreService;
//
//    public RagService(@Qualifier("ragChatClient") ChatClient chatClient, VectorStoreService vectorStoreService) {
//        this.chatClient = chatClient;
//        this.vectorStoreService = vectorStoreService;
//    }

//    public RagResponse ask(RagRequest request) {
//
//        List<Document> documents = vectorStoreService.search(request.question());
//
//        System.out.println("Retrieved Documents: " + documents.size());
//
//        documents.forEach(doc -> {
//            System.out.println("--------------------------------");
//            System.out.println(doc.getText());
//        });
//
//        String context = documents.stream()
//                .map(Document::getText)
//                .collect(Collectors.joining("\n\n-----------------------\n\n"));
//
////        Java text block (""")
//        String prompt = """
//            You are an expert SQL tutor.
//
//            Use ONLY the context below to answer the user's question.
//
//            If the answer is partially available in the context, summarize it clearly.
//
//            Do NOT use outside knowledge.
//
//            If the answer truly does not exist in the context, say:
//            "I couldn't find that information in the provided documents."
//
//            ========================
//            CONTEXT
//            ========================
//
//            %s
//
//            ========================
//            QUESTION
//            ========================
//
//            %s
//
//            ========================
//            ANSWER
//            ========================
//            """.formatted(context, request.question());
//
//        String answer = chatClient.prompt()
//                .user(prompt)
//                .call()
//                .content();
//
//        return new RagResponse(answer);
//    }
//}
