package org.gachon.checkmate.domain.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ProfileImageType {

    PROFILE_1("https://checkmate-dormitory-bucket.s3.ap-northeast-2.amazonaws.com/checkmate-profile1.png"),
    PROFILE_2("https://example.com/profile2.jpg"),
    PROFILE_3("https://example.com/profile3.jpg");

    private final String imageUrl;
}
