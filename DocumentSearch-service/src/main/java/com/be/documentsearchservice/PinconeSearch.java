package com.be.documentsearchservice;

import com.be.documentsearchservice.service.EmbeddingService;
import io.pinecone.clients.Index;
import io.pinecone.configs.PineconeConfig;
import io.pinecone.configs.PineconeConnection;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// pinecone연동 시도용
public class PinconeSearch {



    public static QueryResponseWithUnsignedIndices main(String[] args) {
            ConfigurableApplicationContext ctx = SpringApplication.run(DocumentSearchServiceApplication.class, args);

            PineconeConfig config = new PineconeConfig("pcsk_58CvBb_U2dfMdm48R9ayMTLRZ9E5Zn1xhf4GJ7wK1vWu1BWVKUHKRsbeVXExWbhzo73sYq");
            config.setHost("https://dongmunseodap-document-index-dev-1a3x1qg.svc.aped-4627-b74a.pinecone.io");
            PineconeConnection connection = new PineconeConnection(config);

            Index index = new Index(connection, "dongmunseodap-document-index-dev");

            EmbeddingService embeddingService = ctx.getBean(EmbeddingService.class);

            float[] embeddingArray = embeddingService.embed("테스트 텍스트");

            List<Float> queryVec = IntStream.range(0, embeddingArray.length).mapToObj(i -> embeddingArray[i])
                    .collect(Collectors.toList());

            //List<Float> query = Arrays.asList(0.0236663818359375f, -0.032989501953125f, -0.01041412353515625f, 0.0086669921875f);
            QueryResponseWithUnsignedIndices queryResponse = index.query(3, queryVec, null, null, null, "__default__", null, false, true);
            return queryResponse;
    }

}
