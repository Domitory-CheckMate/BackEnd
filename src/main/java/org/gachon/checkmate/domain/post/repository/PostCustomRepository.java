package org.gachon.checkmate.domain.post.repository;

import org.gachon.checkmate.domain.post.dto.support.PostDetailDto;
import org.gachon.checkmate.domain.post.dto.support.PostSearchCondition;
import org.gachon.checkmate.domain.post.dto.support.PostSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostCustomRepository {
    Optional<PostDetailDto> findPostDetail(Long postId);
    Page<PostSearchDto> searchPosts(PostSearchCondition condition);
    Page<PostSearchDto> searchTextPost(String text, Pageable pageable);
}
