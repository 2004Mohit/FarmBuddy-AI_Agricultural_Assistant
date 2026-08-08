package org.pm.backendspringai.dto;

import java.util.UUID;

public record AuthResponse(
        UUID userId,
        String username,
        String email
) {
}
