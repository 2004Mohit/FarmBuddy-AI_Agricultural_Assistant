package org.pm.backendspringai.controller;

import jakarta.validation.Valid;
import org.pm.backendspringai.dto.farm.FarmRequest;
import org.pm.backendspringai.dto.farm.FarmResponse;
import org.pm.backendspringai.entity.Farm;
import org.pm.backendspringai.service.FarmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/farms")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @PostMapping
    public ResponseEntity<FarmResponse> createFarm(
            @Valid @RequestBody FarmRequest request,
            Authentication authentication) {

        Farm farm = farmService.createFarm(
                authentication.getName(),
                request.getName(),
                request.getLocation(),
                request.getArea());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(farm));
    }

    @GetMapping
    public ResponseEntity<List<FarmResponse>> getMyFarms(Authentication authentication) {

        List<FarmResponse> farms = farmService
                .getMyFarms(authentication.getName())
                .stream()
                .map(FarmController::toResponse)
                .toList();

        return ResponseEntity.ok(farms);

    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmResponse> getFarm(
            @PathVariable UUID id,
            Authentication authentication) {

        Farm farm = farmService.getMyFarm(
                authentication.getName(),
                id
        );

        return ResponseEntity.ok(toResponse(farm));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmResponse> updateFarm(
            @PathVariable UUID id,
            @Valid @RequestBody FarmRequest request,
            Authentication authentication) {

        Farm farm = farmService.updateFarm(
                authentication.getName(),
                id,
                request.getName(),
                request.getLocation(),
                request.getArea()
        );

        return ResponseEntity.ok(toResponse(farm));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(
            @PathVariable UUID id,
            Authentication authentication) {

        farmService.deleteFarm(
                authentication.getName(),
                id
        );

        return ResponseEntity.noContent().build();
    }

    private static FarmResponse toResponse(Farm farm) {

        return new FarmResponse(
                farm.getId(),
                farm.getName(),
                farm.getLocation(),
                farm.getArea(),
                farm.getCreatedAt(),
                farm.getUpdatedAt()
        );
    }

}
