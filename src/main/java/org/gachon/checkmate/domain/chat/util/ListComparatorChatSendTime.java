package org.gachon.checkmate.domain.chat.util;

import org.gachon.checkmate.domain.chat.dto.ChatRoomListDto;

import java.util.Comparator;

public class ListComparatorChatSendTime implements Comparator<ChatRoomListDto> {
    @Override
    public int compare(ChatRoomListDto o1, ChatRoomListDto o2) {
        if(o1.lastChatInfo().sendTime() == null || o2.lastChatInfo().sendTime() == null) {
            return 0;
        }
        return o2.lastChatInfo().sendTime().compareTo(o1.lastChatInfo().sendTime());
    }
}
