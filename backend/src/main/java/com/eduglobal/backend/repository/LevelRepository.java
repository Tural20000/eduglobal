package com.eduglobal.backend.repository;

import com.eduglobal.backend.enums.Level;
import com.eduglobal.backend.entity.LevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LevelRepository extends JpaRepository<LevelEntity, Long> {
	Optional<LevelEntity> findByName(Level name);
}
