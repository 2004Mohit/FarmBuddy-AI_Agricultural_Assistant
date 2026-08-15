package org.pm.backendspringai.dto.crop;

import java.time.LocalDateTime;
import java.util.UUID;

public record CropDiagnosisResponse(
        UUID id,
        String cropName,
        String diseaseName,
        String healthStatus,
        String severity,
        Double confidence,
        String symptoms,
        String pesticideRecommendation,
        String fertilizerRecommendation,
        String organicRemedy,
        String prevention,
        LocalDateTime diagnosedAt
) {
}