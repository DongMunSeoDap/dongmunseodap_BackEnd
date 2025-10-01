package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.dto.ChatResponseDto;
import com.be.documentsearchservice.dto.QueryRequest;
import com.be.documentsearchservice.dto.RagChunkDto;
import com.be.documentsearchservice.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "RAG & Chat", description = "Pinecone 검색, GPT 응답, Elasticsearch 저장 테스트")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class RagChunkController {

    private final ChatService chatService;

    // --- RagChunkEntity 테스트 ---
    @Operation(summary = "Chunk 저장", description = "rag-chunks 인덱스에 한 건 저장")
    @ApiResponse(responseCode = "200", description = "저장된 Chunk",
            content = @Content(schema = @Schema(implementation = RagChunkDto.class)))
    @PostMapping(value = "/chunks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RagChunkDto> saveChunk(@Validated @RequestBody RagChunkDto dto) {
        return chatService.saveChunk(dto);
    }

    @Operation(summary = "Chunk 단건 조회", description = "vectorDBId로 조회")
    @GetMapping(value = "/chunks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RagChunkDto> getChunk(@PathVariable String id) {
        return chatService.findById(id);
    }

    @Operation(summary = "Chunk 전체 조회", description = "rag-chunks 전체 조회(테스트용)")
    @GetMapping(value = "/chunks", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<RagChunkDto> allChunks() {
        return chatService.findAllChunks();
    }

    // --- Chat + History 저장 통합 테스트 ---
    @Operation(
            summary = "질문 → GPT 응답 생성 + 근거 저장 + 히스토리 저장",
            description = """
                1) Pinecone에서 topK=5 근거 검색
                2) 근거를 ES(rag-chunks)에 저장
                3) GPT로 답변 생성
                4) ChatHistory(chat-history)에 질문/답변/토큰/모델 저장
                """)
    @ApiResponse(responseCode = "200", description = "챗봇 응답",
            content = @Content(schema = @Schema(implementation = ChatResponseDto.class)))
    @PostMapping(value = "/chat/ask", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ChatResponseDto ask(@Validated @RequestBody QueryRequest req) {
        return chatService.search_ongoing(req);
    }

    // 리액티브로 응답을 끝까지 저장 완료 후 반환하고 싶으면 아래 엔드포인트를 사용
    @Operation(summary = "[옵션] 저장 완료까지 기다렸다가 응답", description = "백그라운드 저장이 아니라 저장 완료 후 응답을 원할 때 사용")
    @PostMapping(value = "/chat/ask-sync", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ChatResponseDto> askSync(@Validated @RequestBody QueryRequest req) {
        // 기존 메서드가 동기라 우선 그대로 감싸서 반환(필요시 서비스 메서드를 Mono로 리팩토링 권장)
        return Mono.fromCallable(() -> chatService.search_ongoing(req));
    }
}
