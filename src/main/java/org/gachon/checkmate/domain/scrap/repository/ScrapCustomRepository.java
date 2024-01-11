package org.gachon.checkmate.domain.scrap.repository;

import org.gachon.checkmate.domain.post.dto.support.PostSearchDto;
import org.gachon.checkmate.domain.scrap.dto.support.ScrapSearchCondition;
import org.springframework.data.domain.Page;

public interface ScrapCustomRepository {
    Page<PostSearchDto> searchMyScrapPosts(ScrapSearchCondition condition);
}
