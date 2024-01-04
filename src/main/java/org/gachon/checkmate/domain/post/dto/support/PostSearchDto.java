package org.gachon.checkmate.domain.post.dto.support;

import com.querydsl.core.annotations.QueryProjection;
import org.gachon.checkmate.domain.checkList.entity.PostCheckList;
import org.gachon.checkmate.domain.post.entity.ImportantKeyType;
import org.gachon.checkmate.domain.post.entity.SimilarityKeyType;

import java.time.LocalDate;

public record PostSearchDto(
        Long postId,
        String title,
        String content,
        ImportantKeyType importantKey,
        SimilarityKeyType similarityKey,
        LocalDate endDate,
        int scrapCount,
        PostCheckList postCheckList
) {
    @QueryProjection
    public PostSearchDto(Long postId, String title, String content, ImportantKeyType importantKey, SimilarityKeyType similarityKey, LocalDate endDate, int scrapCount, PostCheckList postCheckList) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.importantKey = importantKey;
        this.similarityKey = similarityKey;
        this.endDate = endDate;
        this.scrapCount = scrapCount;
        this.postCheckList = postCheckList;
    }
}
