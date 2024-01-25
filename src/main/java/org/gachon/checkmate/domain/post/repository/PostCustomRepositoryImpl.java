package org.gachon.checkmate.domain.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.member.entity.GenderType;
import org.gachon.checkmate.domain.member.entity.UserState;
import org.gachon.checkmate.domain.post.dto.support.*;
import org.gachon.checkmate.domain.post.entity.DormitoryType;
import org.gachon.checkmate.domain.post.entity.ImportantKeyType;
import org.gachon.checkmate.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static org.gachon.checkmate.domain.checkList.entity.QPostCheckList.postCheckList;
import static org.gachon.checkmate.domain.member.entity.QUser.user;
import static org.gachon.checkmate.domain.post.entity.QPost.post;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PostDetailDto> findPostDetail(Long postId) {
        return Optional.ofNullable(queryFactory
                .select(new QPostDetailDto(
                        user.id,
                        user.major,
                        user.mbtiType,
                        user.gender,
                        post.roomType,
                        post.dormitoryType,
                        user.name,
                        user.profile,
                        post.postCheckList
                ))
                .from(post)
                .leftJoin(post.postCheckList, postCheckList)
                .leftJoin(post.user, user)
                .where(
                        eqPostId(postId)
                )
                .orderBy(post.id.desc())
                .fetchOne());
    }

    @Override
    public Page<PostSearchDto> searchPosts(PostSearchCondition condition) {
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
                        user.gender,
                        post.postState
                ))
                .from(post)
                .leftJoin(post.postCheckList, postCheckList)
                .leftJoin(post.user, user)
                .where(
                        eqImportantKey(condition.importantKeyType()),
                        eqGenderType(condition.genderType()),
                        eqDormitoryType(condition.dormitoryType()),
                        validateUserState()
                )
                .orderBy(post.id.desc())
                .fetch();

        JPAQuery<Post> countQuery = queryFactory
                .selectFrom(post)
                .leftJoin(post.postCheckList, postCheckList)
                .leftJoin(post.user, user)
                .where(
                        eqImportantKey(condition.importantKeyType()),
                        eqGenderType(condition.genderType()),
                        eqDormitoryType(condition.dormitoryType()),
                        validateUserState()
                );
        return PageableExecutionUtils.getPage(content, condition.pageable(), countQuery::fetchCount);
    }

    @Override
    public Page<PostSearchDto> searchPostsWithPaging(PostPagingSearchCondition condition) {
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
                        user.gender,
                        post.postState
                ))
                .from(post)
                .leftJoin(post.postCheckList, postCheckList)
                .leftJoin(post.user, user)
                .where(
                        containTextCondition(condition.text()),
                        eqUserId(condition.selectedUser()),
                        validateUserState()
                )
                .offset(condition.pageable().getOffset())
                .limit(condition.pageable().getPageSize())
                .orderBy(post.id.desc())
                .fetch();

        JPAQuery<Post> countQuery = queryFactory
                .selectFrom(post)
                .from(post)
                .leftJoin(post.postCheckList, postCheckList)
                .leftJoin(post.user, user)
                .where(
                        containTextCondition(condition.text()),
                        eqUserId(condition.selectedUser()),
                        validateUserState()
                );
        return PageableExecutionUtils.getPage(content, condition.pageable(), countQuery::fetchCount);
    }

    private BooleanExpression eqUserId(Long userId) {
        return userId != null ? user.id.eq(userId) : null;
    }

    private BooleanExpression eqPostId(Long postId) {
        return post.id.eq(postId);
    }

    private BooleanExpression eqGenderType(GenderType genderType) {
        return genderType != null ? user.gender.eq(genderType) : null;
    }

    private BooleanExpression eqDormitoryType(DormitoryType dormitoryType) {
        return dormitoryType != null ? post.dormitoryType.eq(dormitoryType) : null;
    }

    private BooleanExpression eqImportantKey(ImportantKeyType importantKeyType) {
        return importantKeyType != null ? post.importantKeyType.eq(importantKeyType) : null;
    }

    private BooleanExpression containTextCondition(String text) {
        return hasText(text) ? post.title.contains(text) : null;
    }

    private BooleanExpression validateUserState() {
        return user.userState.eq(UserState.JOIN);
    }
}
