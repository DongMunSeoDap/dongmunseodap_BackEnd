package com.be.documentuploadservice.repository;

import com.be.documentuploadservice.entity.UploadedFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileElasticSearchRepository extends ElasticsearchRepository<UploadedFile, Long> {


}

