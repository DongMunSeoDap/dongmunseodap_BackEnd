package com.be.documentuploadservice.global.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class S3Config {

  private AWSCredentials awsCredentials;

  @Value("${cloud.aws.credentials.access-key}")
  private String accessKey;

  @Value("${cloud.aws.credentials.secret-key}")
  private String secretKey;

  @Value("${cloud.aws.region.static}")
  private String region;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  @Value("${cloud.aws.s3.path.documents}")
  private String userDocumentsPath;

  @PostConstruct // 의존성 주입이 완료된 후 실행(access/seceret key 의존성 주입 후 사용 목적)
  public void init() { // AWS S3에 접근하기 위한 자격 증명 객체 초기화
    // access/seceret key 의존성 주입이 완료 되어야 BasicAWSCredentials 객체 사용 가능
    this.awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
  }

  @Bean
  public AmazonS3 amazonS3() { // AWS S3에 접근할 수 있는 클라이언트 객체를 빈으로 등록
    AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
    return AmazonS3ClientBuilder.standard()
        .withRegion(region)
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .build();
  }

  @Bean
  public AWSCredentialsProvider awsCredentialsProvider() { // 고정된 자격 증명을 제공
    return new AWSStaticCredentialsProvider(awsCredentials);
  }
}