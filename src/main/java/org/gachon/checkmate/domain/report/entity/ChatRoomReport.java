package org.gachon.checkmate.domain.report.entity;

import jakarta.persistence.*;
import lombok.*;
import org.gachon.checkmate.domain.chat.entity.ChatRoom;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.post.entity.Post;
import org.gachon.checkmate.domain.scrap.entity.Scrap;
import org.gachon.checkmate.global.common.BaseTimeEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class ChatRoomReport extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_report_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
    @Column(name = "reason")
    private String reason;

    public static ChatRoomReport createChatRoomReport(User user, ChatRoom chatRoom, String reason) {
        return ChatRoomReport.builder()
                .user(user)
                .chatRoom(chatRoom)
                .reason(reason)
                .build();
    }
}
