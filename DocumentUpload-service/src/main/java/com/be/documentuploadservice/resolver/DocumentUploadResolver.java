package com.be.documentuploadservice.resolver;

import com.be.documentuploadservice.dto.request.DocumentMetaInput;
import com.be.documentuploadservice.dto.request.EventContextInput;
import com.be.documentuploadservice.dto.response.UploadResponse;
import com.be.documentuploadservice.entity.PathName;
import com.be.documentuploadservice.global.reponse.BaseResponse;
import com.be.documentuploadservice.service.DocumentUploadService;
import graphql.schema.DataFetchingEnvironment;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class DocumentUploadResolver {

  private final DocumentUploadService documentUploadService;

  // S3에 업로드 된 파일 전체 조회
  @QueryMapping
  public List<String> getS3Files(@Argument PathName pathName) {
    return documentUploadService.getAllS3Files(pathName);
  }

}
