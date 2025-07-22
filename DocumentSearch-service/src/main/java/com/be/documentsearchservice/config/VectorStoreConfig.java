package com.be.documentsearchservice.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pinecone.PineconeVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {
    @Bean
    public VectorStore pineconeVectorStore(EmbeddingModel embeddingModel) {
        return PineconeVectorStore.builder(embeddingModel)
                .apiKey("pcsk_4sPMm5_QbgeMF251gB9Bfd8m8FRq7G8G8kqvt5ysFXjwwPmoyR8CwMbdhcYi3VwV4Mr4Hw")
                .indexName("dongmunseodap-document-index-dev\n")
                .namespace("manual-document") // the free tier doesn't support namespaces.
                .contentFieldName("document_content") // optional field to store the original content. Defaults to `document_content`
                .build();
    }
}
