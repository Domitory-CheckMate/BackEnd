package org.gachon.checkmate.domain.chat.repository;

import org.gachon.checkmate.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String>, ChatRoomCustomRepository {
}
