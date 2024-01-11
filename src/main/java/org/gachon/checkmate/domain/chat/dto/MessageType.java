package org.gachon.checkmate.domain.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MessageType {
    CHAT("CHAT"),
    NEW_CHAT_NOTIFICATION("NEW_CHAT_NOTIFICATION"),
    ROOM_ENTER("ROOM_ENTER"),
    ROOM_LIST("ROOM_LIST"),
    CHAT_LIST("CHAT_LIST"),
    NOT_READ_COUNT("NOT_READ_COUNT");

    private final String desc;
}
