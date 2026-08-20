package org.pm.backendspringai.dto.farm;

import java.time.LocalDateTime;
import java.util.UUID;

public class FarmResponse {

    private UUID id;
    private String name;
    private String location;
    private Double area;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FarmResponse() {
    }

    public FarmResponse(UUID id, String name, String location, Double area, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.area = area;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Double getArea() {
        return area;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
