package org.gachon.checkmate.domain.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "chatRoom")
/**
 * 채팅을 보내고 읽음/안읽음 표시를 하기 위해 유저의 채팅방 입장 상황을 redis로 받기 위한 객체입니다.
 * .채팅방에 입장할 때 LiveChatRoom에 유저의 저장을 하고 Unsubscribe하거나 disconnect됐을 때 삭제합니다
 */
public class LiveChatRoom {

    @Id
    private String id;

    @Indexed
    private String chatRoomId;

    @Indexed
    private Long userId;


    public static LiveChatRoom createLiveChatRoom(String chatRoomId, Long userId) {
        return LiveChatRoom.builder()
                .chatRoomId(chatRoomId)
                .userId(userId)
                .build();
    }
}
