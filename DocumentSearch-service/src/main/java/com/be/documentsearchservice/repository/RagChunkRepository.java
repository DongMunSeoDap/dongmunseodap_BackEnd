package com.be.documentsearchservice.repository;

import com.be.documentsearchservice.entity.RagChunkEntity;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface RagChunkRepository
        extends ReactiveElasticsearchRepository<RagChunkEntity, String> {}
