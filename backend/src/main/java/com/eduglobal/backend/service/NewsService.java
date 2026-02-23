package com.eduglobal.backend.service;

import com.eduglobal.backend.dto.NewsDto;

import java.util.List;

public interface NewsService {
	List<NewsDto> findLatest(int limit);
}
