package com.eduglobal.backend.repository;

import com.eduglobal.backend.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Long> {

	List<WordEntity> findAllByOrderByWordAsc();

	boolean existsByWordIgnoreCase(String word);
}
