package org.gachon.checkmate.domain.chat.mongorepository;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.chat.dto.ChatLastMessageDto;
import org.gachon.checkmate.domain.chat.entity.Chat;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

import static org.gachon.checkmate.domain.chat.dto.ChatLastMessageDto.createEmptyChat;

@RequiredArgsConstructor
public class ChatCustomRepositoryImpl implements ChatCustomRepository {
    private final MongoTemplate mongoTemplate;

    /**
     * 채팅방의 이전 채팅들을 확인하는 메소드
     */
    @Override
    public Slice<Chat> findBeforeChatList(final String chatRoomId, final Pageable pageable) {
        Query query = new Query()
                .with(pageable)
                .skip(pageable.getPageSize() * pageable.getPageNumber()) // offset
                .limit(pageable.getPageSize()+1);
        query.with(Sort.by(Sort.Order.desc("sendTime")));

        query.addCriteria(Criteria.where("chatRoomId").is(chatRoomId));
        List<Chat> chats = mongoTemplate.find(query, Chat.class, "chatting");

        return new SliceImpl<>(chats, pageable, hasNextPage(chats, pageable.getPageSize()));
    }

    /**
     * 채팅방에 입장하고 안읽은 메시지를 읽음 처리해주는 메소드
     */
    @Override
    public void updateChatRead(final String chatRoomId, final Long userId) {
        Query query = new Query();
        Update update = new Update();

        query.addCriteria(Criteria.where("chatRoomId").is(chatRoomId)
                .and("sender").ne(userId));

        update.set("isRead", true);
        mongoTemplate.updateMulti(query, update, Chat.class);
    }

    /**
     * 채팅방에 남겨진 마지막 채팅 메시지를 가져오는 메소드
     */
    @Override
    public ChatLastMessageDto findLastChatRoomContent(final String chatRoomId) {

        Pageable pageable = PageRequest.of(0, 1);

        Query query = new Query()
                .with(pageable)
                .skip(pageable.getPageSize() * pageable.getPageNumber()) // offset
                .limit(pageable.getPageSize());
        query.with(Sort.by(Sort.Order.desc("sendTime")));
        query.addCriteria(Criteria.where("chatRoomId").is(chatRoomId));

        List<Chat> chats = mongoTemplate.find(query, Chat.class, "chatting");
        return chats.isEmpty() ? createEmptyChat() : ChatLastMessageDto.of(chats.get(0));
    }

    /**
     * 채팅방에 유저가 읽지않은 메시지의 수를 가져오는 메소드
     */
    @Override
    public Long findUserNotReadCount(final String chatRoomId, final Long userId) {
        Query query = new Query();

        query.addCriteria(Criteria.where("chatRoomId").is(chatRoomId)
                .and("senderId").ne(userId)
                .and("isRead").is(false));

        return mongoTemplate.count(query, Chat.class);
    }

    private boolean hasNextPage(List<Chat> chats, int pageSize) {
        if (chats.size() > pageSize) {
            chats.remove(pageSize);
            return true;
        }
        return false;
    }
}
