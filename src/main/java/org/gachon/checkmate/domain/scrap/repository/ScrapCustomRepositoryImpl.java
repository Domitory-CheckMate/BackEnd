package org.gachon.checkmate.domain.scrap.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.member.entity.UserState;
import org.gachon.checkmate.domain.post.dto.support.PostSearchDto;
import org.gachon.checkmate.domain.post.dto.support.QPostSearchDto;
import org.gachon.checkmate.domain.scrap.dto.support.ScrapSearchCondition;
import org.gachon.checkmate.domain.scrap.entity.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static org.gachon.checkmate.domain.checkList.entity.QPostCheckList.postCheckList;
import static org.gachon.checkmate.domain.member.entity.QUser.user;
import static org.gachon.checkmate.domain.post.entity.QPost.post;
import static org.gachon.checkmate.domain.scrap.entity.QScrap.scrap;

@RequiredArgsConstructor
public class ScrapCustomRepositoryImpl implements ScrapCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostSearchDto> searchMyScrapPosts(ScrapSearchCondition condition) {
        List<PostSearchDto> content = queryFactory
                .select(new QPostSearchDto(
                        post.id,
                        post.title,
                        post.content,
                        post.importantKeyType,
                        post.similarityKeyType,
                        post.endDate,
                        post.scrapList.size(),
                        postCheckList,
                        user.gender
                ))
                .from(scrap)
                .leftJoin(scrap.post, post)
                .leftJoin(scrap.post.postCheckList, postCheckList)
                .leftJoin(scrap.user, user)
                .where(
                        eqUserId(condition.userId()),
                        validateUserState()
                )
                .offset(condition.pageable().getOffset())
                .limit(condition.pageable().getPageSize())
                .fetch();

        JPAQuery<Scrap> countQuery = queryFactory
                .selectFrom(scrap)
                .leftJoin(scrap.post, post)
                .leftJoin(scrap.post.postCheckList, postCheckList)
                .leftJoin(scrap.user, user)
                .where(
                        eqUserId(condition.userId()),
                        validateUserState()
                );

        return PageableExecutionUtils.getPage(content, condition.pageable(), countQuery::fetchCount);
    }

    private BooleanExpression eqUserId(Long userId) {
        return userId != null ? user.id.eq(userId) : null;
    }

    private BooleanExpression validateUserState() {
        return user.userState.eq(UserState.JOIN);
    }

}
