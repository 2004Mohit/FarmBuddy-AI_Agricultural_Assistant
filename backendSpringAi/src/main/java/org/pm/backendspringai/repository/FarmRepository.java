package org.pm.backendspringai.repository;

import org.pm.backendspringai.entity.Farm;
import org.pm.backendspringai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FarmRepository extends JpaRepository<Farm, UUID> {

    List<Farm> findAllByUser(User user);

    Optional<Farm> findByIdAndUser(UUID id, User user);

    boolean existsByIdAndUser(UUID id, User user);
}
