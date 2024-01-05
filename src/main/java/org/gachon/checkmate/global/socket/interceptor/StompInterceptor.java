package org.gachon.checkmate.global.socket.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gachon.checkmate.domain.chat.entity.LiveChatRoom;
import org.gachon.checkmate.domain.chat.repository.LiveChatRoomRepository;
import org.gachon.checkmate.domain.member.repository.UserRepository;
import org.gachon.checkmate.global.socket.SocketJwtProvider;
import org.gachon.checkmate.global.socket.error.SocketErrorCode;
import org.gachon.checkmate.global.socket.error.SocketException;
import org.gachon.checkmate.global.socket.error.SocketNotFoundException;
import org.gachon.checkmate.global.socket.error.SocketUnauthorizedException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.gachon.checkmate.global.socket.error.SocketErrorCode.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompInterceptor implements ChannelInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    public static final String DEFAULT_PATH = "/queue/chat/";
    private final SocketJwtProvider socketJwtProvider;
    private final UserRepository userRepository;
    private final LiveChatRoomRepository liveChatRoomRepository;

    // websocket을 통해 들어온 요청이 처리 되기전 실행된다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        if (StompCommand.CONNECT.equals(command)) { // websocket 연결요청 -> JWT 인증
            // JWT 인증 부분
            Long userId = getUserByAuthorizationHeader(
                    accessor.getFirstNativeHeader("Authorization")
            );

            setValue(accessor, "userId", userId);
            log.info("[CONNECT]:: userId : " + userId);

        } else if (StompCommand.SEND.equals(command)) {
            // 매 요청마다 accessToken 검증
            validateToken(accessor);
        } else if (StompCommand.SUBSCRIBE.equals(command)) { // 채팅방 구독요청(진입)
            Long userId = (Long)getValue(accessor, "userId");
            if(checkIsDestinationChatRoom(accessor)) {
                String enterRoomId = parseRoomIdFromPath(accessor);
                setValue(accessor, "roomId", enterRoomId);
            }
            log.debug("userId : " + userId);
        } else if (StompCommand.UNSUBSCRIBE.equals(command)) {
            Long userId = (Long)getValue(accessor, "userId");
            if(checkIsDestinationChatRoom(accessor)) {
                deleteLiveChatRoom(accessor);
                deleteValue(accessor, "roomId");
            }
            log.info("UNSUBSCRIBE userId : {}", userId);
        } else if (StompCommand.DISCONNECT == command) { // Websocket 연결 종료
            Long userId = (Long)getValue(accessor, "userId");
            deleteLiveChatRoom(accessor);
            log.info("DISCONNECTED userId : {}", userId);
        }
        return message;
    }

    private Long getUserByAuthorizationHeader(String authHeaderValue) {
        String accessToken = getTokenByAuthorizationHeader(authHeaderValue);
        Long userId = socketJwtProvider.getSubject(accessToken);
        validateUserExist(userId);
        return userId;
    }

    private void validateUserExist(Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new SocketNotFoundException(USER_NOT_FOUND);
        }
    }

    private void deleteLiveChatRoom(StompHeaderAccessor accessor) {
        if(isChatRoomAttributeExist(accessor)) {
            String roomId = (String) getValue(accessor, "roomId");
            Long userId = (Long) getValue(accessor, "userId");
            List<LiveChatRoom> liveChatRooms = liveChatRoomRepository.findAllByUserId(userId);
            liveChatRoomRepository.deleteAll(liveChatRooms);
        }
    }

    private String getTokenByAuthorizationHeader(String authHeaderValue) {
        if (Objects.isNull(authHeaderValue) || authHeaderValue.isBlank()) {
            throw new SocketUnauthorizedException(SocketErrorCode.USER_NOT_AUTHORIZED);
        }
        String accessToken = SocketJwtProvider.extractToken(authHeaderValue);
        socketJwtProvider.validateAccessToken(accessToken);

        return accessToken;
    }

    private void validateToken(StompHeaderAccessor accessor) {
        String authHeaderValue = accessor.getFirstNativeHeader("Authorization");
        if (Objects.isNull(authHeaderValue) || authHeaderValue.isBlank()) {
            throw new SocketUnauthorizedException(SocketErrorCode.USER_NOT_AUTHORIZED);
        }
        String accessToken = SocketJwtProvider.extractToken(authHeaderValue);
        socketJwtProvider.validateAccessToken(accessToken);
        Long userId = socketJwtProvider.getSubject(accessToken);
        Long originalUserId = (Long) getValue(accessor, "userId");
        if(!originalUserId.equals(userId)) {
            throw new SocketUnauthorizedException(SocketErrorCode.NOT_ORIGINAL_USER);
        }
    }

    private Boolean checkIsDestinationChatRoom(StompHeaderAccessor accessor) {
        return accessor.getDestination().contains(DEFAULT_PATH);
    }

    private String parseRoomIdFromPath(StompHeaderAccessor accessor) {
        String destination = accessor.getDestination();
        return destination.substring(DEFAULT_PATH.length());
    }

    private Object getValue(StompHeaderAccessor accessor, String key) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        Object value = sessionAttributes.get(key);

        if (Objects.isNull(value)) {
            throw new SocketException(SOCKET_SERVER_ERROR);
        }
        return value;
    }

    private Boolean isChatRoomAttributeExist(StompHeaderAccessor accessor) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        Object value = sessionAttributes.get("roomId");
        return !Objects.isNull(value);
    }

    private void setValue(StompHeaderAccessor accessor, String key, Object value) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        sessionAttributes.put(key, value);
    }

    private void deleteValue(StompHeaderAccessor accessor, String key) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        sessionAttributes.remove(key);
    }

    private Map<String, Object> getSessionAttributes(StompHeaderAccessor accessor) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if (Objects.isNull(sessionAttributes)) {
            throw new SocketException(SESSION_ATTRIBUTE_NOT_FOUND);
        }
        return sessionAttributes;
    }

}

