package org.gachon.checkmate.domain.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.gachon.checkmate.global.common.BaseTimeEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Getter
@Table(name = "chat_room")
/**
 * 채팅방에 포함된 두명의 사용자를 판단하기 위해 만들었습니다.
 * 이때 채팅방의 id는 autoIncrement 되는것이 아닌 두 사용자의 id를 사용해 만들어지게 됩니다.
 * ex) user1 id = 1, user2 id = 2라면 채팅방 id는 "1+2" 이 되고 항상 작은 숫자가 앞으로 가게 됩니다.
 * ex) user1 id = 10, user2 id = 3 , 채팅방 id = "3+10"
 */
// ChatRoom의 경우 DB에 저장해서 해당 채팅방 id를 통해 채팅방 속 두명의 사용자를 판단하기 위해 만들었습니다.
public class ChatRoom extends BaseTimeEntity {

    @Id
    @Column(name = "chat_room_id", length = 100)
    private String id;

    @Column(name = "first_member_id")
    private Long firstMemberId;

    @Column(name = "second_member_id")
    private Long secondMemberId;

    public static ChatRoom createChatRoom(String id, Long firstMemberId, Long secondMemberId) {
        return ChatRoom.builder()
                .id(id)
                .firstMemberId(firstMemberId)
                .secondMemberId(secondMemberId)
                .build();
    }

}
