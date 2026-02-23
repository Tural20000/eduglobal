package com.eduglobal.backend.repository;

import com.eduglobal.backend.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

	List<NewsEntity> findTop20ByOrderByCreatedAtDesc();
}
