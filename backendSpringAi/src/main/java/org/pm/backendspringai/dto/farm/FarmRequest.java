package org.pm.backendspringai.dto.farm;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FarmRequest {

    @NotBlank(message = "Farm name is required")
    private String name;

    @NotBlank(message = "Farm location is required")
    private String location;

    @NotNull(message = "Farm area is required")
    @DecimalMin(value = "0.01", message = "Farm area must be greater than 0")
    private Double area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }
}
