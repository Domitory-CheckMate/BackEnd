package org.gachon.checkmate.domain.chat.mongorepository;


import org.gachon.checkmate.domain.chat.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String>, ChatCustomRepository {
    
}
