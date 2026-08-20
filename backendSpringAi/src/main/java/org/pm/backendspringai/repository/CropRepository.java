package org.pm.backendspringai.repository;

import org.pm.backendspringai.entity.Crop;
import org.pm.backendspringai.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CropRepository extends JpaRepository<Crop, UUID> {

    List<Crop> findAllByFarm(Farm farm);

    Optional<Crop> findByIdAndFarm(UUID id, Farm farm);

    boolean existsByIdAndFarm(UUID id, Farm farm);

}
