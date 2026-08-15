package org.pm.backendspringai.dto.crop;

// For deserializing Gemini's structured response

public record CropDiagnosisAIResult(
        String cropName,
        String diseaseName,
        String healthStatus,
        String severity,
        Double confidence,
        String symptoms,
        String pesticideRecommendation,
        String fertilizerRecommendation,
        String organicRemedy,
        String prevention
) {
}