package org.pm.backendspringai.dto;

public record ChatRequest(
        String conversationId,
        String message
) {
}