package org.gachon.checkmate.domain.chat.dto;

import lombok.Builder;


@Builder
public record ChatRoomListDto(
        ChatLastMessageDto lastChatInfo,
        Long notReadCount,
        ChatRoomListUserInfoDto userInfo
) {
    public static ChatRoomListDto of(ChatLastMessageDto lastChatInfo, Long notReadCount, ChatRoomListUserInfoDto chatRoomListUserInfoDto) {
        return ChatRoomListDto.builder()
                .lastChatInfo(lastChatInfo)
                .notReadCount(notReadCount)
                .userInfo(chatRoomListUserInfoDto)
                .build();
    }
}
