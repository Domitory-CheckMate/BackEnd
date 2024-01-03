package org.gachon.checkmate.domain.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.post.dto.support.PostSearchDto;
import org.gachon.checkmate.domain.post.dto.support.QPostSearchDto;
import org.gachon.checkmate.domain.post.entity.ImportantKeyType;
import org.gachon.checkmate.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.gachon.checkmate.domain.checkList.entity.QPostCheckList.postCheckList;
import static org.gachon.checkmate.domain.post.entity.QPost.post;

@RequiredArgsConstructor
@Repository
public class PostQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    public Page<PostSearchDto> searchKeyPost(ImportantKeyType importantKeyType, Pageable pageable) {
        List<PostSearchDto> content = queryFactory
                .select(new QPostSearchDto(
                        post.title,
                        post.content,
                        post.importantKeyType,
                        post.similarityKeyType,
                        post.endDate,
                        post.scrapList.size(),
                        postCheckList
                ))
                .from(post)
                .leftJoin(post.postCheckList, postCheckList)
                .where(
                        containKeyWordCondition(importantKeyType),
                        validatePostDate()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Post> countQuery = queryFactory
                .selectFrom(post);
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    public Page<PostSearchDto> searchTextPost(String text, Pageable pageable) {
        List<PostSearchDto> content = queryFactory
                .select(new QPostSearchDto(
                        post.title,
                        post.content,
                        post.importantKeyType,
                        post.similarityKeyType,
                        post.endDate,
                        post.scrapList.size(),
                        postCheckList
                ))
                .from(post)
                .leftJoin(post.postCheckList, postCheckList)
                .where(
                        containTextCondition(text),
                        validatePostDate()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Post> countQuery = queryFactory
                .selectFrom(post);
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression containKeyWordCondition(ImportantKeyType importantKeyType) {
        return post.importantKeyType.eq(importantKeyType);
    }

    private BooleanExpression containTextCondition(String text) {
        return post.title.contains(text);
    }

    private BooleanExpression validatePostDate() {
        return post.endDate.before(LocalDate.now());
    }
}
