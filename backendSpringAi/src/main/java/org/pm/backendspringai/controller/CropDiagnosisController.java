package org.pm.backendspringai.controller;

import org.pm.backendspringai.dto.crop.CropDiagnosisResponse;
import org.pm.backendspringai.service.CropDiagnosisService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/crops")
public class CropDiagnosisController {

    private final CropDiagnosisService cropDiagnosisService;

    public CropDiagnosisController(CropDiagnosisService cropDiagnosisService) {
        this.cropDiagnosisService = cropDiagnosisService;
    }

    @PostMapping(
            value = "/diagnose",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<CropDiagnosisResponse> diagnoseCrop(
            @RequestParam("images") List<MultipartFile> images,
            Authentication authentication) {

        if(images == null || images.isEmpty()) {
            throw new IllegalArgumentException("At least 1 image is required");
        }

        if(images.size() > 5) {
            throw new IllegalArgumentException("Maximum 5 images are allowed");
        }

        for(MultipartFile image : images) {

            if (image.isEmpty()) {
                throw new IllegalArgumentException("Image cannot be empty");
            }

            String contentType = image.getContentType();

            if(contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/webp"))) {

                throw new IllegalArgumentException(
                        "Only JPEG, PNG and WEBP images are allowed"
                );
            }

            if (image.getSize() > 10 * 1024 * 1024) {
                throw new IllegalArgumentException(
                        "Each image must be less than 10 MB"
                );
            }
        }

        return ResponseEntity.ok(
                cropDiagnosisService.analyzeCropImages(
                        images,
                        authentication.getName())
        );
    }

    @GetMapping("/history")
    public ResponseEntity<List<CropDiagnosisResponse>> getHistory(Authentication authentication) {

        return ResponseEntity.ok(cropDiagnosisService.getUserHistory(authentication.getName()));
    }
}
