package org.gachon.checkmate.domain.post.dto.support;

import lombok.Builder;
import org.gachon.checkmate.domain.checkList.dto.support.CheckListEnumDto;
import org.gachon.checkmate.domain.post.entity.DormitoryType;
import org.gachon.checkmate.domain.post.entity.ImportantKeyType;
import org.gachon.checkmate.domain.post.entity.RoomType;
import org.gachon.checkmate.domain.post.entity.SimilarityKeyType;

import java.time.LocalDate;

@Builder
public record PostEnumDto(
        String title,
        String content,
        ImportantKeyType importantKey,
        SimilarityKeyType similarityKey,
        RoomType roomType,
        DormitoryType dormitoryType,
        LocalDate endDate,
        CheckListEnumDto checkList
) {
}
