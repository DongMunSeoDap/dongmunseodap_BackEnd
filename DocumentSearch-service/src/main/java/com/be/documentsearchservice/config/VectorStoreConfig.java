package com.be.documentsearchservice.config;


import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pinecone.PineconeVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

    private EmbeddingModel embeddingModel;

    public VectorStoreConfig(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @Bean
    public VectorStore pineconeVectorStore(EmbeddingModel embeddingModel) {
        String apiKey = "pcsk_4sPMm5_QbgeMF251gB9Bfd8m8FRq7G8G8kqvt5ysFXjwwPmoyR8CwMbdhcYi3VwV4Mr4Hw";

        String indexName = "dongmunseodap-document-index-dev";

        String namespace = "manual-document";

        String contentType = "document_content";
        return PineconeVectorStore.builder(embeddingModel)
                .apiKey(apiKey)
                .indexName(indexName)
                .namespace(namespace) // the free tier doesn't support namespaces.
                .contentFieldName(contentType) // optional field to store the original content. Defaults to `document_content`
                .build();
    }
}
