package com.be.documentsearchservice.consumerconfigserver;

import com.be.documentsearchservice.dto.DocumentUploadedEvent;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    // KafkaListener 설정 - 메시지를 안정적으로 소비하기 위함.

    // Kakka 관련 기능 활성화 및 설정 클래스 정의
    // properties에 설정된 kafka주소 읽어옴
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // 로그에 찍어보며 주소가 잘 들어오는지 확인용
    @PostConstruct
    public void init() {
        System.out.println("✅ Kafka bootstrapServers: " + bootstrapServers);
    }
    // 카프카 consumer groupID - kafka consumer 설정을 외부 파일에서 불러오는 변수
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    // 자동 커밋 여부
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private boolean autoComit;

    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;


    // Kafka 리스너를 위한 컨테이너 팩토리 정의
    // 컨테이너를 생성하주는 팩토리
    @Bean
    public ConsumerFactory<String, DocumentUploadedEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl); // application.yml에서 설정
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DocumentUploadedEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DocumentUploadedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        return factory;
    }





}