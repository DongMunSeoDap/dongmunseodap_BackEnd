package com.be.documentuploadservice.repository;

import com.be.documentuploadservice.entity.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoucumentRepository extends ElasticsearchRepository<Document, String> {


}

