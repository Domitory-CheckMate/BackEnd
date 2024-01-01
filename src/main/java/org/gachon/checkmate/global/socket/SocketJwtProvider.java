package org.gachon.checkmate.global.socket;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.gachon.checkmate.global.error.ErrorCode;
import org.gachon.checkmate.global.error.exception.UnauthorizedException;
import org.gachon.checkmate.global.socket.error.SocketException;
import org.gachon.checkmate.global.socket.error.SocketUnauthorizedException;
import org.gachon.checkmate.global.socket.error.SocketErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Getter
@Component
public class SocketJwtProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-token-expire-time}")
    private long ACCESS_TOKEN_EXPIRE_TIME;
    @Value("${jwt.refresh-token-expire-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    private static final String BEARER_TYPE = "Bearer";

    public String getIssueToken(Long userId, boolean isAccessToken) {
        if (isAccessToken) return generateToken(userId, ACCESS_TOKEN_EXPIRE_TIME);
        else return generateToken(userId, REFRESH_TOKEN_EXPIRE_TIME);
    }

    public void validateAccessToken(String accessToken) {
        try {
            getJwtParser().parseClaimsJws(accessToken);
        } catch (ExpiredJwtException e) {
            throw new SocketUnauthorizedException(SocketErrorCode.EXPIRED_ACCESS_TOKEN);
        } catch (Exception e) {
            throw new SocketUnauthorizedException(SocketErrorCode.INVALID_ACCESS_TOKEN_VALUE);
        }
    }

    public static String extractToken(String authHeaderValue) {
        if (authHeaderValue.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            return authHeaderValue.substring(BEARER_TYPE.length()).trim();
        }
        return null;
    }

    public Claims getClaimsFormToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e){
            throw new SocketException(SocketErrorCode.INVALID_ACCESS_TOKEN_VALUE);
        }
    }

    public Long getSubject(String token) {
        return Long.valueOf(getJwtParser().parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    private String generateToken(Long userId, long tokenTime) {
        final Date now = new Date();
        final Date expiration = new Date(now.getTime() + tokenTime);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build();
    }

    private Key getSigningKey() {
        String encoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(encoded.getBytes());
    }
}
