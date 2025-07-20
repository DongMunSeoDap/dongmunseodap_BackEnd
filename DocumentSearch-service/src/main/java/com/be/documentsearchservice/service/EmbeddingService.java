package com.be.documentsearchservice.service;


import com.be.documentsearchservice.dto.EmbeddingResponse;
import com.google.common.primitives.Floats;
import io.pinecone.clients.Index;
import io.pinecone.configs.PineconeConfig;
import io.pinecone.configs.PineconeConnection;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmbeddingService {

    private final EmbeddingModel embeddingClient;
    private EmbeddingService embeddingService;
    private EmbeddingResponse embeddingResponse;
    private static final Logger log = LoggerFactory.getLogger(EmbeddingService.class);
    public EmbeddingService(EmbeddingModel embeddingClient) {
        this.embeddingClient = embeddingClient;
    }

    // open api 연동
    public float[] embed(String text) {
        return embeddingClient.embed(text);
    }

    public QueryResponseWithUnsignedIndices search(EmbeddingResponse embeddingResponse) {

        float[] input = embeddingResponse.embedding;
        if (input == null) {
            throw new IllegalArgumentException("Embedding is null. Did you call /embedding first?");
        }
        log.info(">>> first 5 values = {}", Arrays.toString(Arrays.copyOf(input, 5)));
        PineconeConfig config = new PineconeConfig("pcsk_58CvBb_U2dfMdm48R9ayMTLRZ9E5Zn1xhf4GJ7wK1vWu1BWVKUHKRsbeVXExWbhzo73sYq");
        config.setHost("https://dongmunseodap-document-index-dev-1a3x1qg.svc.aped-4627-b74a.pinecone.io");
        PineconeConnection connection = new PineconeConnection(config);

        Index index = new Index(connection, "dongmunseodap-document-index-dev");

        List<Float> list = Floats.asList(input);
        QueryResponseWithUnsignedIndices queryResponse =  index.query(10, list, null, null, null, "manual-document", null, false, true);

        return queryResponse;

    }



}
