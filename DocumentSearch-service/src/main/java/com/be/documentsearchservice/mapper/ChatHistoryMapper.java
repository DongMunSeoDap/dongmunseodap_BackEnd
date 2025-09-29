package com.be.documentsearchservice.mapper;

import com.be.documentsearchservice.dto.ChatHistoryDto;
import com.be.documentsearchservice.entity.ChatHistory;

public final class ChatHistoryMapper {
    private ChatHistoryMapper() {}

    public static ChatHistoryDto toDto(ChatHistory e) {
        if (e == null) return null;
        return ChatHistoryDto.builder()
                .chatId(e.getChatId())
                .conversationId(e.getConversationId())
                .userId(e.getUserId())
                .userQuery(e.getUserQuery())
                .ragAnswer(e.getRagAnswer())
                .timestamp(e.getTimestamp())
                .model(e.getModel())
                .promptVersion(e.getPromptVersion())
                .promptTokens(e.getPromptTokens())
                .completionTokens(e.getCompletionTokens())
                .totalTokens(e.getTotalTokens())
                .build();
    }

    public static ChatHistory toEntity(ChatHistoryDto d) {
        if (d == null) return null;
        return ChatHistory.builder()
                .chatId(d.getChatId())
                .conversationId(d.getConversationId())
                .userId(d.getUserId())
                .userQuery(d.getUserQuery())
                .ragAnswer(d.getRagAnswer())
                .timestamp(d.getTimestamp())
                .model(d.getModel())
                .promptVersion(d.getPromptVersion())
                .promptTokens(d.getPromptTokens())
                .completionTokens(d.getCompletionTokens())
                .totalTokens(d.getTotalTokens())
                .build();
    }
}
