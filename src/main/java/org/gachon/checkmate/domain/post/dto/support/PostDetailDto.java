package org.gachon.checkmate.domain.post.dto.support;

import com.querydsl.core.annotations.QueryProjection;
import org.gachon.checkmate.domain.checkList.entity.PostCheckList;
import org.gachon.checkmate.domain.member.entity.GenderType;
import org.gachon.checkmate.domain.member.entity.MbtiType;
import org.gachon.checkmate.domain.member.entity.ProfileImageType;

public record PostDetailDto(
        Long memberId,
        String major,
        MbtiType mbti,
        GenderType gender,
        String name,
        String profile,
        PostCheckList postCheckList
) {
    @QueryProjection
    public PostDetailDto(Long memberId, String major, MbtiType mbti, GenderType gender, String name, String profile, PostCheckList postCheckList) {
        this.memberId = memberId;
        this.major = major;
        this.mbti = mbti;
        this.gender = gender;
        this.name = name;
        this.profile = profile;
        this.postCheckList = postCheckList;
    }
}
