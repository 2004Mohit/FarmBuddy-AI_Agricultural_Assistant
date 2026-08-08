package org.pm.backendspringai.dto;

public record LoginRequest(
        String email,
        String password
) {
}