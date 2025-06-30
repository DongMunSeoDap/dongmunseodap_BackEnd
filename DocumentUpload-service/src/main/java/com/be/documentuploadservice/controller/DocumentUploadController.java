package com.be.documentuploadservice.controller;

import com.be.documentuploadservice.dto.response.UploadResponse;
import com.be.documentuploadservice.entity.PathName;
import com.be.documentuploadservice.global.reponse.BaseResponse;
import com.be.documentuploadservice.service.DocumentUploadService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class DocumentUploadController {

  private final DocumentUploadService documentUploadService;

  @Operation(summary = "문서 업로드 API", description = "문서를 업로드하고 메타정보를 리턴하는 API")
  @PostMapping(value = "/document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BaseResponse<UploadResponse>> uploadImage(
      @RequestParam PathName pathName, MultipartFile file) {

    UploadResponse uploadResponse = documentUploadService.uploadDocuments(pathName, file);
    return ResponseEntity.ok(BaseResponse.success("문서 업로드에 성공했습니다.", uploadResponse));
  }
}
