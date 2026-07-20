package org.pm.backendspringai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory memory) {
        return builder
                .defaultAdvisors(
                    MessageChatMemoryAdvisor.builder(memory).build()
                )
                .build();
    }

//    Temporary Stateless RAG due to cause of ConversationID needing error
    @Bean
    public ChatClient ragChatClient(ChatClient.Builder builder) {
        return builder.build();
    }
}
