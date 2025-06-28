package com.be.documentsearchservice.dto;

import lombok.Data;

@Data
public class DocumentUploadedEvent {
    private String version;
    private String event_type;
    private String trace_id;
    private Payload payload;
    private Meta meta;

    @Data
    public static class Payload {
        private String document_id;
        private String file_name;
        private String s3_path;
        private String uploaded_by;
        private Long uploaded_at; // timestamp in milliseconds
    }

    @Data
    public static class Meta {
        private String mime_type;
        private String language;
    }
}
