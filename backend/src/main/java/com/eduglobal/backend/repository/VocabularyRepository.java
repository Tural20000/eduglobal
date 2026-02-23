package com.eduglobal.backend.repository;

import com.eduglobal.backend.entity.VocabularyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyRepository extends JpaRepository<VocabularyEntity, Long> {
    List<VocabularyEntity> findByLevelId(Long levelId);
}
