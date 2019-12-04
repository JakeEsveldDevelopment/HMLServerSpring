package com.jakeesveld.hmlserver.service;

import com.jakeesveld.hmlserver.model.Message;
import com.jakeesveld.hmlserver.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepo messageRepo;

    @Autowired
    public MessageServiceImpl(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public Optional<Message> findById(long id) {
        return messageRepo.findById(id);
    }

    @Override
    public Message save(Message message) {
        return messageRepo.save(message);
    }

    @Override
    public void delete(long id) {
        messageRepo.deleteById(id);
    }
}
