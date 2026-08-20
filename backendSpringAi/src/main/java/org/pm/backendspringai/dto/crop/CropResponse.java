package org.pm.backendspringai.dto.crop;

import org.pm.backendspringai.entity.CropStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CropResponse {

    private UUID id;
    private UUID farmId;
    private String name;
    private String variety;
    private LocalDate sowingDate;
    private LocalDate expectedHarvestDate;
    private Double area;
    private CropStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CropResponse() {
    }

    public CropResponse(UUID id,
                        UUID farmId,
                        String name,
                        String variety,
                        LocalDate sowingDate,
                        LocalDate expectedHarvestDate,
                        Double area, CropStatus status,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt) {
        this.id = id;
        this.farmId = farmId;
        this.name = name;
        this.variety = variety;
        this.sowingDate = sowingDate;
        this.expectedHarvestDate = expectedHarvestDate;
        this.area = area;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getFarmId() {
        return farmId;
    }

    public String getName() {
        return name;
    }

    public String getVariety() {
        return variety;
    }

    public LocalDate getSowingDate() {
        return sowingDate;
    }

    public LocalDate getExpectedHarvestDate() {
        return expectedHarvestDate;
    }

    public Double getArea() {
        return area;
    }

    public CropStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
