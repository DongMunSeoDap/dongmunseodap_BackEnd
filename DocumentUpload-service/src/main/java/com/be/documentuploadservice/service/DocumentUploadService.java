package com.be.documentuploadservice.service;

import com.be.documentuploadservice.dto.request.DocumentMetaInput;
import com.be.documentuploadservice.dto.request.EventContextInput;
import com.be.documentuploadservice.dto.response.UploadResponse;
import com.be.documentuploadservice.dto.response.UploadResponse.UploadStatus;
import com.be.documentuploadservice.entity.Document;
import com.be.documentuploadservice.mapper.DocumentMapper;
import com.be.documentuploadservice.repository.DoucumentRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentUploadService {

  private final DoucumentRepository documentRepository;
  private final DocumentMapper documentMapper;
  private final S3Client s3Client;   // AWS SDK v2 S3 클라이언트 ,S3 API 호출용

  private final String bucketName = "dongmunseodap-test-bucket";


  // 파일 업로드 시 처리 로직
  public UploadResponse fileUpload(MultipartFile file,
      DocumentMetaInput meta, EventContextInput context) {

    // S3 업로드 로직
    String documentId = UUID.randomUUID().toString();
    String key = "documents/" + documentId + "/" + file.getOriginalFilename(); // 객체 키
    String s3Path; // S3 경로 저장용

    boolean uploadSuccess = false; // 업로드 초기 상태

    try {
      PutObjectRequest putObjectRequest = PutObjectRequest.builder()
          .bucket(bucketName)
          .key(key)
          .contentType(file.getContentType())
          .build();

      s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

      uploadSuccess = true;
      s3Path = "s3://" + bucketName + "/" + key;

    } catch (Exception e) {
      e.printStackTrace();
      uploadSuccess = false;
      s3Path = null;
    }

    Document doc = Document.builder()
        .documentId(documentId)
        .fileName(file.getOriginalFilename())
        .mimeType(file.getContentType())
        .language(meta.getLanguage())
        .uploadedBy(context.getUploadedBy())
        .uploadedAt(Instant.now())
        .status(uploadSuccess ? UploadStatus.SUCCESS : UploadStatus.FAILURE)
        .message(uploadSuccess ? null : "Upload failure")
        .s3Path(s3Path)
        .build();

    if(uploadSuccess) {
      documentRepository.save(doc);
    }

    log.info("Uploaded to S3: {}", s3Path);

    return documentMapper.toUploadResponse(doc);
  }

  // 문서 언어 감지 로직
 /* public String languageDetector(MultipartFile file) {
    try (PDDocument document = PDDocument.load(file.getInputStream())) {
      PDFTextStripper stripper = new PDFTextStripper();
      String text = stripper.getText(document);

      LanguageDetector detector = new TikaLanguageDetector();
      LanguageResult result = detector.detect(text);

      return result.isReasonablyCertain() ? result.getLanguage() : "und";
    } catch (Exception e) {
      e.printStackTrace(); // 디버깅용
      return "und";
    }
  }*/
}
