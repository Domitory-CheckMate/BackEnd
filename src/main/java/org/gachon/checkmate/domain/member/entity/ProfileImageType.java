package org.gachon.checkmate.domain.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ProfileImageType {

    PROFILE_1("https://checkmate-dormitory-bucket.s3.ap-northeast-2.amazonaws.com/checkmate-profile1.png"),
    PROFILE_2("https://checkmate-dormitory-bucket.s3.ap-northeast-2.amazonaws.com/checkmate-profile2.png"),
    PROFILE_3("https://checkmate-dormitory-bucket.s3.ap-northeast-2.amazonaws.com/checkmate-profile3.png");

    private final String imageUrl;
}
