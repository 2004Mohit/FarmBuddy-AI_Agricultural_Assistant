package org.pm.backendspringai.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "crop_diagnoses")
public class CropDiagnosis  {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String cropName;

    @Column(nullable = false)
    private String diseaseName;

    @Column(nullable = false)
    private String healthStatus;

    @Column(nullable = false)
    private String severity;

    private Double confidence;

    @Column(columnDefinition = "TEXT")
    private String symptoms;

    @Column(columnDefinition = "TEXT")
    private String pesticideRecommendation;

    @Column(columnDefinition = "TEXT")
    private String fertilizerRecommendation;

    @Column(columnDefinition = "TEXT")
    private String organicRemedy;

    @Column(columnDefinition = "TEXT")
    private String prevention;

    @Column(nullable = false)
    private LocalDateTime diagnosedAt;

    public CropDiagnosis() {
    }

    @PrePersist
    protected void onCreate() {
        diagnosedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getPesticideRecommendation() {
        return pesticideRecommendation;
    }

    public void setPesticideRecommendation(String pesticideRecommendation) {
        this.pesticideRecommendation = pesticideRecommendation;
    }

    public String getFertilizerRecommendation() {
        return fertilizerRecommendation;
    }

    public void setFertilizerRecommendation(String fertilizerRecommendation) {
        this.fertilizerRecommendation = fertilizerRecommendation;
    }

    public String getOrganicRemedy() {
        return organicRemedy;
    }

    public void setOrganicRemedy(String organicRemedy) {
        this.organicRemedy = organicRemedy;
    }

    public String getPrevention() {
        return prevention;
    }

    public void setPrevention(String prevention) {
        this.prevention = prevention;
    }

    public LocalDateTime getDiagnosedAt() {
        return diagnosedAt;
    }

}
