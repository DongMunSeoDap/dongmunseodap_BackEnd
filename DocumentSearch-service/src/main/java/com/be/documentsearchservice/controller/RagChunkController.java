package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.dto.RagChunkDto;
import com.be.documentsearchservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/chunks")
@RequiredArgsConstructor
public class RagChunkController {

    private final ChatService chatService;

    @PostMapping
    public Mono<RagChunkDto> save(@RequestBody RagChunkDto dto) {
        return chatService.saveChunk(dto);
    }

    @GetMapping
    public Flux<RagChunkDto> findAll() {
        return chatService.findAllChunks();
    }

    @GetMapping("/{id}")
    public Mono<RagChunkDto> findById(@PathVariable String id) {
        return chatService.findById(id);
    }
}
