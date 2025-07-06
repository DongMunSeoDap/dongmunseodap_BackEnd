package com.be.documentsearchservice.service;

import com.be.documentsearchservice.DocumentSearchServiceApplication;
import io.pinecone.clients.Index;
import io.pinecone.configs.PineconeConfig;
import io.pinecone.configs.PineconeConnection;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import org.springframework.boot.SpringApplication;

import java.util.Arrays;
import java.util.List;

public class PinconeSearchService {
    public static void main(String[] args) {
        SpringApplication.run(DocumentSearchServiceApplication.class, args);
        PineconeConfig config = new PineconeConfig("pcsk_58CvBb_U2dfMdm48R9ayMTLRZ9E5Zn1xhf4GJ7wK1vWu1BWVKUHKRsbeVXExWbhzo73sYq");
        config.setHost("https://dongmunseodap-document-index-dev-1a3x1qg.svc.aped-4627-b74a.pinecone.io");
        PineconeConnection connection = new PineconeConnection(config);
        Index index = new Index(connection, "dongmunseodap-document-index-dev");
        List<Float> query = Arrays.asList(0.0236663818359375f, -0.032989501953125f, -0.01041412353515625f, 0.0086669921875f);
        QueryResponseWithUnsignedIndices queryResponse = index.query(3, query, null, null, null, "example-namespace", null, false, true);
        System.out.println(queryResponse);
    }
}
