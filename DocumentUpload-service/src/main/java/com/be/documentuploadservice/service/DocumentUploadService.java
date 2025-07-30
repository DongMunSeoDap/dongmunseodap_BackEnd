package com.be.documentuploadservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.be.documentuploadservice.dto.response.UploadResponse;
import com.be.documentuploadservice.entity.PathName;
import com.be.documentuploadservice.entity.UplaodStatus;
import com.be.documentuploadservice.exception.S3ErrorCode;
import com.be.documentuploadservice.global.config.S3Config;
import com.be.documentuploadservice.global.exception.CustomException;
import com.be.documentuploadservice.mapper.DocumentMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentUploadService {

  private final AmazonS3 amazonS3; // AWS SDK에서 제공하는 S3 클라이언트 객체
  private final S3Config s3Config; // 버킷 이름과 경로 등 설정 정보

  // 문서 업로드
  public UploadResponse uploadDocuments(PathName pathName, MultipartFile file)
  {

    String pdfUrl = uploadFile(pathName, file); // 웹에서 접근 가능한 문서 객체 Url

    return UploadResponse.builder()
        .documentName(file.getOriginalFilename())
        .s3Key(createKeyName(pathName, file.getOriginalFilename())) // 고유성 보장
        .status(UplaodStatus.SUCCESS)
        .uploadedBy("admin") // 추후에 사용자 id로 확장
        .uploadedAt(LocalDateTime.now())
        .message("문서 업로드에 성공했습니다.")
        .pdfUrl(pdfUrl)
        .build();

  }

  // 파일 업로드 로직 + 객체 경로 반환
  public String uploadFile(PathName pathName, MultipartFile file) {

    validateFile(file); // 파일 유료성 검사

    String originalFilename = file.getOriginalFilename(); // 기존 파일 이름
    if(originalFilename == null || originalFilename.isBlank()) {
      throw new CustomException(S3ErrorCode.FILE_NOT_FOUND);

    }

    String KeyName = createKeyName(pathName, originalFilename); // S3 파일 경로 생성 (경로+이름)

    // 메타 데이터 설정
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(file.getSize()); // 파일 크기
    metadata.setContentType(file.getContentType()); // 파일 타입 (application/pdf)

    // S3에 파일 업로드
    try {
      amazonS3.putObject(
          new PutObjectRequest(s3Config.getBucket(), KeyName, file.getInputStream(), metadata));

      log.info("업로드 성공");

      // 웹에서 직접 접근 가능한 http/https url 문자열 반환
      return amazonS3.getUrl(s3Config.getBucket(), KeyName).toString();
    } catch (Exception e) {
      log.error("S3 upload 중 오류 발생", e);
      throw new CustomException(S3ErrorCode.FILE_SERVER_ERROR);
    }

  }

  // S3에 업로드된 파일 전체 조회 (S3 Url 반환)
  public List<String> getAllS3Files(PathName pathName) {
    String prefix = switch (pathName) {
      case DOCUMENTS ->s3Config.getUserDocumentsPath();
    };

    log.info(">>>> S3 prefix: {}", prefix);

    try {
      return amazonS3
          .listObjectsV2(
              new ListObjectsV2Request().withBucketName(s3Config.getBucket()).withPrefix(prefix))
          .getObjectSummaries()
          .stream()
          // 한글, 공백, 득수문자가 url에 포함될 경우 s3가 자동으로 인코딩하여 반환(디코딩 가능)
          .map(obj -> amazonS3.getUrl(s3Config.getBucket(), obj.getKey()).toString())
          .collect(Collectors.toList());
    } catch (Exception e) {
      log.error("S3 파일 목록 조회 중 오류 발생", e);
      throw new CustomException(S3ErrorCode.FILE_SERVER_ERROR);
    }
  }

  // 업로드 파일 유효성 검사
  public void validateFile(MultipartFile file) {
    if (file.getSize() > 5 * 1024 * 1024) { // 파일 크기 5MB 이하 업로드 가능
      throw new CustomException(S3ErrorCode.FILE_SIZE_INVALID);
    }

    String contentType =file.getContentType(); // 파일의 mime타입 반환
    // getcontentType()은 보안상 좋지 않다는 의견 -> 파일 확장자 검사 로직 변경해야할까요?

    if(contentType == null || !contentType.equals("application/pdf")) { // pdf 형식만 허용
      throw new CustomException(S3ErrorCode.FILE_TYPE_INVALID);
    }
  }

  // S3 파일 경로 생성 (여기서 keyName은 {documents/원본파일 이름})
  public String createKeyName(PathName pathName, String originalFilename) {
    String basePath = switch (pathName) {
      case DOCUMENTS ->s3Config.getUserDocumentsPath();
    };

    String uuid = UUID.randomUUID().toString(); // key 값을 식별 할 uuid 문자열 생성
    String fileExtension = getFileExtension(originalFilename); // 파일 확장자 추출

    return basePath + "/" + uuid + fileExtension; // 고유성은 보장하지만 원본파일 이름 추척이 힘들거 같음
  }

  // 파일 확장자 추출(".pdf"로 추출)
  private String getFileExtension(String filename) {
    if (filename == null || !filename.contains(".")) {
      return "";
    }
    return filename.substring(filename.lastIndexOf("."));
  }
}