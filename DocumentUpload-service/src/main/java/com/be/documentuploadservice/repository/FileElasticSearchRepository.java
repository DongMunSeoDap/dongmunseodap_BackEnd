package com.be.documentuploadservice.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends ElasticsearchRepository<Document, String> {


}

