package org.gachon.checkmate.global.socket.interceptor;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gachon.checkmate.global.socket.SocketJwtProvider;
import org.gachon.checkmate.global.socket.error.SocketException;
import org.gachon.checkmate.global.socket.error.SocketUnauthorizedException;
import org.gachon.checkmate.global.socket.error.SocketErrorCode;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static org.gachon.checkmate.global.socket.error.SocketErrorCode.SESSION_ATTRIBUTE_NOT_FOUND;
import static org.gachon.checkmate.global.socket.error.SocketErrorCode.SOCKET_SERVER_ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompInterceptor implements ChannelInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    public static final String DEFAULT_PATH = "/queue/chat/";
    private final SocketJwtProvider socketJwtProvider;

    // websocket을 통해 들어온 요청이 처리 되기전 실행된다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        Random random = new Random();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        if (StompCommand.CONNECT.equals(command)) { // websocket 연결요청 -> JWT 인증

            // JWT 인증 부분은 나중에 추가하도록 하겠습니다.
//            User user = getUserByAuthorizationHeader(
//                    accessor.getFirstNativeHeader("Authorization"));

            // Exception 처리 테스트용
            getUserByAuthorizationHeader(
                    accessor.getFirstNativeHeader("Authorization"));
            // 현재는 임의의 값으로 두었습니다.
            Long userId = (long) random.nextInt(1000);
            setValue(accessor, "userId", userId);
            log.info("[CONNECT]:: userId : " + userId);
//            setValue(accessor, "username", user.getNickname());
//            setValue(accessor, "profileImgUrl", user.getProfileImgUrl());

        } else if (StompCommand.SUBSCRIBE.equals(command)) { // 채팅방 구독요청(진입)
            Long userId = (Long)getValue(accessor, "userId");
            log.debug("userId : " + userId);
            if(checkIsDestinationChatRoom(accessor)) {
                Long roomId = parseRoomIdFromPath(accessor);
                setValue(accessor, "roomId", roomId);
                log.debug("roomId : " + roomId);
            }
        } else if (StompCommand.UNSUBSCRIBE.equals(command)) {
            Long userId = (Long)getValue(accessor, "userId");
            log.info("UNSUBSCRIBE userId : {}", userId);
        } else if (StompCommand.DISCONNECT == command) { // Websocket 연결 종료
            Long userId = (Long)getValue(accessor, "userId");
            log.info("DISCONNECTED userId : {}", userId);
        }
        return message;
    }

    private void getUserByAuthorizationHeader(String authHeaderValue) {
        String accessToken = getTokenByAuthorizationHeader(authHeaderValue);

        Claims claims = socketJwtProvider.getClaimsFormToken(accessToken);
        Long userId = claims.get("userId", Long.class);

//        return userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private String getTokenByAuthorizationHeader(String authHeaderValue) {
        if (Objects.isNull(authHeaderValue) || authHeaderValue.isBlank()) {
            throw new SocketUnauthorizedException(SocketErrorCode.USER_NOT_AUTHORIZED);
        }
        String accessToken = SocketJwtProvider.extractToken(authHeaderValue);
        socketJwtProvider.validateAccessToken(accessToken);

        return accessToken;
    }

    private Boolean checkIsDestinationChatRoom(StompHeaderAccessor accessor) {
        return accessor.getDestination().contains("/queue/chat/");
    }

    private Long parseRoomIdFromPath(StompHeaderAccessor accessor) {
        String destination = accessor.getDestination();
        return Long.parseLong(destination.substring(DEFAULT_PATH.length()));
    }

    private Object getValue(StompHeaderAccessor accessor, String key) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        Object value = sessionAttributes.get(key);

        if (Objects.isNull(value)) {
            throw new SocketException(SOCKET_SERVER_ERROR);
        }
        return value;
    }

    private void setValue(StompHeaderAccessor accessor, String key, Object value) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        sessionAttributes.put(key, value);
    }

    private Map<String, Object> getSessionAttributes(StompHeaderAccessor accessor) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if (Objects.isNull(sessionAttributes)) {
            throw new SocketException(SESSION_ATTRIBUTE_NOT_FOUND);
        }
        return sessionAttributes;
    }

}

