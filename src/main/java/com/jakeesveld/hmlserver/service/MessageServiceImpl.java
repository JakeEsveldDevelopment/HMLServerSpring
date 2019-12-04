package com.jakeesveld.hmlserver.service;

import com.jakeesveld.hmlserver.model.Message;

import java.util.Optional;

public class MessageServiceImpl implements MessageService {
    @Override
    public Optional<Message> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Message save(Message message) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
