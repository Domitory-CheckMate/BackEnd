package org.gachon.checkmate.domain.chat.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.chat.entity.ChatRoom;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.member.entity.UserState;

import java.util.List;

import static org.gachon.checkmate.domain.chat.entity.QChatRoom.chatRoom;

@RequiredArgsConstructor
public class ChatRoomCustomRepositoryImpl implements ChatRoomCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoom> findUserAllChatRoom(Long userId) {
        return queryFactory
                .selectFrom(chatRoom)
                .where(
                        userEnterChatRoom(userId),
                        validateUsersState()
                )
                .fetch();
    }

    private BooleanExpression userEnterChatRoom(Long userId) {
        return chatRoom.firstUser.id.eq(userId)
                .or(chatRoom.secondUser.id.eq(userId));
    }

    private BooleanExpression validateUsersState() {
        return chatRoom.firstUser.userState.eq(UserState.JOIN)
                .and(chatRoom.secondUser.userState.eq(UserState.JOIN));
    }

}
