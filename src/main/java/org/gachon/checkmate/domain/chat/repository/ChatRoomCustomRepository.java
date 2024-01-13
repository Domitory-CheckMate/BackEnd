package org.gachon.checkmate.domain.chat.repository;

import org.gachon.checkmate.domain.chat.entity.ChatRoom;
import org.gachon.checkmate.domain.member.entity.User;

import java.util.List;

public interface ChatRoomCustomRepository {

    List<ChatRoom> findUserAllChatRoom(Long userId);
}
