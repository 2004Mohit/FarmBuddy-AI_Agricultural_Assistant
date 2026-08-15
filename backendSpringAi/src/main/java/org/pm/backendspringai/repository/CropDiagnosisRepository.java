package org.pm.backendspringai.repository;

import org.pm.backendspringai.entity.CropDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CropDiagnosisRepository extends JpaRepository<CropDiagnosis, UUID> {

    List<CropDiagnosis> findByUserIdOrderByDiagnosedAtDesc(UUID userId);
}
