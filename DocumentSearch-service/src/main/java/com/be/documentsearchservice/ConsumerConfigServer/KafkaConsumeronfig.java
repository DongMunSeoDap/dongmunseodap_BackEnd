package com.be.documentsearchservice.ConsumerConfigServer;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@EnableKafka
@Configuration
public class KafkaConsumeronfig {
    // Kakka 관련 기능 활성화 및 설정 클래스 정의
    // properties에 설정된 kafka주소 읽어옴
    @Value("${spring.kafka.bootstrap-servers:}")
    private String bootstrapServers;

    // 로그에 찍어보며 주소가 잘 들어오는지 확인용
    @PostConstruct
    public void init() {
        System.out.println("✅ Kafka bootstrapServers: " + bootstrapServers);
    }
    // 카프카 consumer groupID
    @Value("${spring.kafka.consumer.group-id:}")
    private String groupId;

    // 자동 커밋 여부
    @Value("${spring.kafka.consumer.enable-auto-commit:}")
    private boolean autocomit;

    // 병렬 처리를 위한 스레드풀 설정 주입
    @Autowired
    private TaskExecutionAutoConfiguration taskExecutionAutoConfiguration;

    // 멀티 스레드 동작을 안정적으로 처리하기 위한 설정 추가
    @Configuration //
    public class TaskExecutorConfig {
        @Bean // Spring이 제공하는 인터페이스로, 비동기 작업을 실행할 수 있도록 해준다.
        public TaskExecutor taskExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); // 구현체
            executor.setCorePoolSize(3); // 기본으로 유지될 쓰레드 수
            executor.setMaxPoolSize(5); // 최대 생성 가능한 스레드 수
            executor.setQueueCapacity(100); // 스레드가 모두 바쁠 때 대기할 수 있는 작업의 수
            executor.initialize(); // 스레드 풀 초기화
            return executor;

        }
    }

    // KafkaListener 설정 - 메시지를 안정적으로 소비하기 위함.
    // Kafka 리스너를 위한 컨테이너 팩토리 정의





}