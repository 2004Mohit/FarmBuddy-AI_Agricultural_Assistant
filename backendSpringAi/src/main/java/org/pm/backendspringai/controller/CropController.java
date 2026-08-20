package org.pm.backendspringai.controller;

import jakarta.validation.Valid;
import org.pm.backendspringai.dto.crop.CropRequest;
import org.pm.backendspringai.dto.crop.CropResponse;
import org.pm.backendspringai.entity.Crop;
import org.pm.backendspringai.service.CropService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/farms/{farmId}/crops")
public class CropController {

    private final CropService cropService;

    public CropController(CropService cropService) {
        this.cropService = cropService;
    }

    @PostMapping
    public ResponseEntity<CropResponse> createCrop(
            @PathVariable UUID farmId,
            @Valid @RequestBody CropRequest request,
            Authentication authentication) {

        Crop crop = cropService.createCrop(
                authentication.getName(),
                farmId,
                request.getName(),
                request.getVariety(),
                request.getSowingDate(),
                request.getExpectedHarvestDate(),
                request.getArea()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(crop));
    }

    @GetMapping
    public ResponseEntity<List<CropResponse>> getFarmCrops(
            @PathVariable UUID farmId,
            Authentication authentication) {

        List<CropResponse> crops = cropService
                .getFarmCrops(
                        authentication.getName(),
                        farmId
                )
                .stream()
                .map(CropController::toResponse)
                .toList();

        return ResponseEntity.ok(crops);
    }

    @GetMapping("/{cropId}")
    public ResponseEntity<CropResponse> getCrop(
            @PathVariable UUID farmId,
            @PathVariable UUID cropId,
            Authentication authentication) {

        Crop crop = cropService.getCrop(
                authentication.getName(),
                farmId,
                cropId
        );

        return ResponseEntity.ok(toResponse(crop));
    }

    @PutMapping("/{cropId}")
    public ResponseEntity<CropResponse> updateCrop(
            @PathVariable UUID farmId,
            @PathVariable UUID cropId,
            @Valid @RequestBody CropRequest request,
            Authentication authentication) {

        Crop crop = cropService.updateCrop(
                authentication.getName(),
                farmId,
                cropId,
                request.getName(),
                request.getVariety(),
                request.getSowingDate(),
                request.getExpectedHarvestDate(),
                request.getArea(),
                request.getStatus()
        );

        return ResponseEntity.ok(toResponse(crop));
    }

    @DeleteMapping("/{cropId}")
    public ResponseEntity<Void> deleteCrop(
            @PathVariable UUID farmId,
            @PathVariable UUID cropId,
            Authentication authentication) {

        cropService.deleteCrop(
                authentication.getName(),
                farmId,
                cropId
        );

        return ResponseEntity.noContent().build();
    }

    private static CropResponse toResponse(Crop crop) {

        return new CropResponse(
                crop.getId(),
                crop.getFarm().getId(),
                crop.getName(),
                crop.getVariety(),
                crop.getSowingDate(),
                crop.getExpectedHarvestDate(),
                crop.getArea(),
                crop.getStatus(),
                crop.getCreatedAt(),
                crop.getUpdatedAt()
        );
    }
}
