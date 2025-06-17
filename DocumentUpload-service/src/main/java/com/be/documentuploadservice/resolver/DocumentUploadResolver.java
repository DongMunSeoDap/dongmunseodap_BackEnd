package com.be.documentuploadservice.resolver;

import com.be.documentuploadservice.dto.request.DocumentMetaInput;
import com.be.documentuploadservice.dto.request.EventContextInput;
import com.be.documentuploadservice.dto.response.UploadResponse;
import com.be.documentuploadservice.global.reponse.BaseResponse;
import com.be.documentuploadservice.service.DocumentUploadService;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequiredArgsConstructor
public class DocumentUploadResolver {

  private final DocumentUploadService documentUploadService;

  @MutationMapping
  public BaseResponse<UploadResponse> uploadDocument(
      @Argument MultipartFile file,
      @Argument DocumentMetaInput meta,
      @Argument EventContextInput context,
      DataFetchingEnvironment env) {

    UploadResponse uploadResponse = documentUploadService.fileUpload(file, meta, context);

    return BaseResponse.success("Upload Success", uploadResponse);
  }

}
