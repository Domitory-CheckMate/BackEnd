package org.gachon.checkmate.domain.chat.dto.response;

import lombok.Builder;
import org.gachon.checkmate.domain.chat.dto.ChatRoomListDto;

import java.util.List;


@Builder
public record ChatRoomListResponseDto(
        List<ChatRoomListDto> chatRoomList
) {
    public static ChatRoomListResponseDto of(List<ChatRoomListDto> chatRoomListDto) {
        return ChatRoomListResponseDto.builder()
                .chatRoomList(chatRoomListDto)
                .build();
    }
}
