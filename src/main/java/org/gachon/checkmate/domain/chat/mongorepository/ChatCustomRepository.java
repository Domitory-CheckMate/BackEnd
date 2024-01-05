package org.gachon.checkmate.domain.chat.mongorepository;

import org.gachon.checkmate.domain.chat.dto.ChatLastMessageDto;
import org.gachon.checkmate.domain.chat.entity.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatCustomRepository {

    Slice<Chat> findBeforeChatList(final String chatRoomId, final Pageable pageable);

    void updateChatRead(final String chatRoomId, final Long userId);

    ChatLastMessageDto findLastChatRoomContent(final String chatRoomId);

    Long findUserNotReadCount(final String chatRoomId, final Long userId);
}
