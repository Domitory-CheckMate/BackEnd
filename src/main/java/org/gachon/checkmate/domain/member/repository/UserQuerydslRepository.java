package org.gachon.checkmate.domain.member.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.chat.dto.ChatRoomListUserInfoDto;
import org.gachon.checkmate.domain.chat.dto.ChatUserInfoDto;
import org.gachon.checkmate.domain.chat.dto.QChatRoomListUserInfoDto;
import org.gachon.checkmate.domain.chat.dto.QChatUserInfoDto;
import org.springframework.stereotype.Repository;

import static org.gachon.checkmate.domain.member.entity.QUser.user;
import static org.gachon.checkmate.domain.post.entity.QPost.post;


@RequiredArgsConstructor
@Repository
public class UserQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    public ChatRoomListUserInfoDto findUserChatRoomListInfo(Long userId) {
        return queryFactory
                .select(new QChatRoomListUserInfoDto(
                        user.id,
                        user.name,
                        user.profile,
                        user.major,
                        user.gender,
                        post.endDate
                ))
                .from(user)
                .leftJoin(user.postList, post)
                .where(
                        eqUserId(userId)
                )
                .orderBy(postEndDateDesc())
                .limit(1)
                .fetchOne();
    }
    public ChatUserInfoDto findUserChatUserInfo(Long userId) {
        return queryFactory
                .select(new QChatUserInfoDto(
                        user.id,
                        user.name,
                        user.profile,
                        post.id,
                        post.title,
                        post.endDate
                ))
                .from(user)
                .leftJoin(user.postList, post)
                .where(
                        eqUserId(userId)
                )
                .orderBy(post.endDate.desc())
                .limit(1)
                .fetchOne();
    }

    private static OrderSpecifier<?> postEndDateDesc() {
        return post.endDate != null ? post.endDate.desc() : null;
    }

    private BooleanExpression eqUserId(Long userId) {
        return user.id.eq(userId);
    }
}
