package org.pm.backendspringai.dto.crop;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.pm.backendspringai.entity.CropStatus;

import java.time.LocalDate;

public class CropRequest {

    @NotBlank(message = "Crop name is required")
    private String name;

    private String variety;

    @NotNull(message = "Sowing date is required")
    private LocalDate sowingDate;

    private LocalDate expectedHarvestDate;

    @NotNull(message = "Crop area is required")
    @DecimalMin(value = "0.01", message = "Crop area must be greater than 0")
    private Double area;
    private CropStatus status;

    public CropRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public LocalDate getSowingDate() {
        return sowingDate;
    }

    public void setSowingDate(LocalDate sowingDate) {
        this.sowingDate = sowingDate;
    }

    public LocalDate getExpectedHarvestDate() {
        return expectedHarvestDate;
    }

    public void setExpectedHarvestDate(LocalDate expectedHarvestDate) {
        this.expectedHarvestDate = expectedHarvestDate;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public CropStatus getStatus() {
        return status;
    }

    public void setStatus(CropStatus status) {
        this.status = status;
    }
}
