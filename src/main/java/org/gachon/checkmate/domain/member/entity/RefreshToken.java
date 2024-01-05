package org.gachon.checkmate.domain.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@RedisHash(value = "refreshToken", timeToLive = 60*60*24*7)
public class RefreshToken {
    @Id
    private Long id;
    private String refreshToken;

    public static RefreshToken createRefreshToken(Long userId, String refreshToken) {
        return RefreshToken.builder()
                .id(userId)
                .refreshToken(refreshToken)
                .build();
    }
}