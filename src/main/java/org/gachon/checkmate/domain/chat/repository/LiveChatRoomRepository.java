package org.gachon.checkmate.domain.chat.repository;

import org.gachon.checkmate.domain.chat.entity.LiveChatRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LiveChatRoomRepository extends CrudRepository<LiveChatRoom, String> {

    List<LiveChatRoom> findAllByUserId(Long userId);

    Boolean existsLiveChatRoomByChatRoomIdAndUserId(String chatRoomId, Long userId);

}
