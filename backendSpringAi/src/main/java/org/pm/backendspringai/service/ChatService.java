package org.pm.backendspringai.service;

import org.pm.backendspringai.dto.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }

    public String askAi(ChatRequest chatRequest) {
        return chatClient.prompt()
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        chatRequest.conversationId()
                ))
                .user(chatRequest.message())
                .call()
                .content();
    }

    public Flux<String> streamChat(ChatRequest chatRequest) {
        return chatClient.prompt()
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        chatRequest.conversationId()
                ))
                .user(chatRequest.message())
                .stream()
                .content();
    }
}
