package org.pm.backendspringai.service;

import org.pm.backendspringai.dto.crop.CropDiagnosisAIResult;
import org.pm.backendspringai.dto.crop.CropDiagnosisResponse;
import org.pm.backendspringai.entity.CropDiagnosis;
import org.pm.backendspringai.entity.User;
import org.pm.backendspringai.repository.CropDiagnosisRepository;
import org.pm.backendspringai.repository.UserRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class CropDiagnosisService {

    private final CropDiagnosisRepository cropDiagnosisRepository;
    private final UserRepository userRepository;
    private final ChatClient chatClient;

    public CropDiagnosisService(CropDiagnosisRepository cropDiagnosisRepository, UserRepository userRepository, @Qualifier("ragChatClient") ChatClient chatClient) {
        this.cropDiagnosisRepository = cropDiagnosisRepository;
        this.userRepository = userRepository;
        this.chatClient = chatClient;
    }

    private Media convertToMedia(MultipartFile image) {

        try {
            MediaType mediaType = MediaType.parseMediaType(
                    image.getContentType()
            );

            return Media.builder()
                    .mimeType(mediaType)
                    .data(image.getBytes())
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to process crop image",
                    e
            );
        }
    }

    public CropDiagnosisResponse analyzeCropImages(List<MultipartFile> images, String email) {

        List<Media> mediaList = images.stream()
                .map(this::convertToMedia)
                .toList();

        CropDiagnosisAIResult result = chatClient.prompt()
                .user(user -> user.text("""
                            You are an agricultural crop disease diagnosis assistant.

                            Analyze the provided crop image(s).

                            Identify:
                            - Crop name
                            - Disease name
                            - Health status
                            - Severity
                            - Confidence
                            - Symptoms
                            - Pesticide recommendation
                            - Fertilizer recommendation
                            - Organic remedy
                            - Prevention

                            IMPORTANT:
                            - Do not automatically classify a crop as healthy.
                            - If there are signs of disease, identify the disease.
                            - If the image is unclear, clearly indicate that the diagnosis is uncertain.
                            - If the crop cannot be identified, say "Unknown".
                            - Confidence must be a number between 0 and 100.
                            - Return ONLY the requested structured JSON.
                            """)
                        .media(mediaList.toArray(new Media[0]))
                )
                .call()
                .entity(CropDiagnosisAIResult.class);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CropDiagnosis diagnosis = new CropDiagnosis();

        diagnosis.setUserId(user.getId());
        diagnosis.setCropName(result.cropName());
        diagnosis.setDiseaseName(result.diseaseName());
        diagnosis.setHealthStatus(result.healthStatus());
        diagnosis.setSeverity(result.severity());
        diagnosis.setConfidence(result.confidence());
        diagnosis.setSymptoms(result.symptoms());
        diagnosis.setPesticideRecommendation(
                result.pesticideRecommendation()
        );
        diagnosis.setFertilizerRecommendation(
                result.fertilizerRecommendation()
        );
        diagnosis.setOrganicRemedy(
                result.organicRemedy()
        );
        diagnosis.setPrevention(
                result.prevention()
        );

        CropDiagnosis savedDiagnosis = cropDiagnosisRepository.save(diagnosis);

        return toResponse(savedDiagnosis);
    }

    public List<CropDiagnosisResponse> getUserHistory(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UUID userId = user.getId();

        return cropDiagnosisRepository
                .findByUserIdOrderByDiagnosedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private CropDiagnosisResponse toResponse(CropDiagnosis diagnosis) {

        return new CropDiagnosisResponse(
                diagnosis.getId(),
                diagnosis.getCropName(),
                diagnosis.getDiseaseName(),
                diagnosis.getHealthStatus(),
                diagnosis.getSeverity(),
                diagnosis.getConfidence(),
                diagnosis.getSymptoms(),
                diagnosis.getPesticideRecommendation(),
                diagnosis.getFertilizerRecommendation(),
                diagnosis.getOrganicRemedy(),
                diagnosis.getPrevention(),
                diagnosis.getDiagnosedAt()
        );
    }
}
