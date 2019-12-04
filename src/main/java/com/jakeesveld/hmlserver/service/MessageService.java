package com.jakeesveld.hmlserver.service;

import com.jakeesveld.hmlserver.model.Message;

import java.util.Optional;

public interface MessageService {
    Optional<Message> findById(long id);

    Message save(Message message);

    void delete(long id);


}
