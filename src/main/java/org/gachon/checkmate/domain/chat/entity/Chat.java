package org.gachon.checkmate.domain.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@Entity
@Document(collection = "chatting")
/**
 * 채팅 정보를 담는 객체로 몽고db를 통해 관리됩니다.
 * isRead라는 것을 통해 상대가 이 메시지를 읽었는지 판단합니다.
 */
public class Chat {

    @Id
    @Field(name="_id")
    private String id;

    @Field(name="chat_room_id")
    private String chatRoomId;

    @Field(name="sender_id")
    private Long senderId;

    @Field(name="content")
    private String content;

    @Field(name="is_read")
    private Boolean isRead;

    @LastModifiedDate
    @Field(name="send_time")
    private LocalDateTime sendTime;

    public static Chat createChat(String chatRoomId, Long senderId, String content, Boolean isRead) {
        return Chat.builder()
                .chatRoomId(chatRoomId)
                .senderId(senderId)
                .content(content)
                .isRead(isRead)
                .build();
    }
}
