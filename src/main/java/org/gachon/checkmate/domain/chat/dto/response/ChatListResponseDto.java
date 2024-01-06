package org.gachon.checkmate.domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.gachon.checkmate.domain.chat.dto.ChatMessageDto;
import org.gachon.checkmate.domain.chat.dto.ChatUserInfoDto;
import org.gachon.checkmate.domain.chat.entity.Chat;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class ChatListResponseDto{

    private String chatRoomId;

    private ChatUserInfoDto chatUserInfoDto;

    @Builder.Default
    private List<ChatMessageDto> chatMessageList = new ArrayList<>();

    @Builder.Default
    private Boolean hasNextPage = null;

    @Builder.Default
    private Integer pageNumber = null;

    public static ChatListResponseDto of(String chatRoomId, ChatUserInfoDto chatUserInfoDto) {
        return ChatListResponseDto.builder()
                .chatRoomId(chatRoomId)
                .chatUserInfoDto(chatUserInfoDto)
                .build();
    }

    public void addChatMessage(Chat chat) {
        this.chatMessageList.add(ChatMessageDto.of(chat));
    }

    public void updatePageInfo(Boolean hasNextPage, Integer pageNumber) {
        this.hasNextPage = hasNextPage;
        this.pageNumber = pageNumber;
    }
}
