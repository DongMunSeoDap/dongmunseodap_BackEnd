package com.be.documentsearchservice.repository;

import com.be.documentsearchservice.entity.ChatHistory;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface ChatHistoryRepository extends ReactiveElasticsearchRepository<ChatHistory, String> {
}
