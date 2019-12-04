package com.jakeesveld.hmlserver.service;

import com.jakeesveld.hmlserver.model.Conversation;

import java.util.List;
import java.util.Optional;

public interface ConversationService {

    Optional<Conversation> findById(long id);

    List<Conversation> findAllByUser(long userId);

    Conversation save(Conversation conversation);

    Conversation update(Conversation conversation, long id);

    void delete(long id);
}
