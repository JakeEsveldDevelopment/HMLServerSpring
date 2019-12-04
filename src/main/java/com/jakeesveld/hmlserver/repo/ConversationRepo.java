package com.jakeesveld.hmlserver.repo;

import com.jakeesveld.hmlserver.model.Conversation;
import com.jakeesveld.hmlserver.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConversationRepo extends CrudRepository<Conversation, Long> {
    List<Conversation> findByParticipantsContains(User user);
}
