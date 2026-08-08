package org.pm.backendspringai.dto;

public record LoginResponse(
        String token,
        String message
) {
}