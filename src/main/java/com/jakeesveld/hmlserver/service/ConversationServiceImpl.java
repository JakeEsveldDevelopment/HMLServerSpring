package com.jakeesveld.hmlserver.service;

import com.jakeesveld.hmlserver.model.Conversation;
import com.jakeesveld.hmlserver.model.User;
import com.jakeesveld.hmlserver.repo.ConversationRepo;
import com.jakeesveld.hmlserver.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityExistsException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepo conversationRepo;
    private final UserRepo userRepo;

    @Autowired
    public ConversationServiceImpl(ConversationRepo conversationRepo, UserRepo userRepo) {
        this.conversationRepo = conversationRepo;
        this.userRepo = userRepo;
    }


    @Override
    public Optional<Conversation> findById(long id) {
        return conversationRepo.findById(id);
    }

    @Override
    public List<Conversation> findAllByUser(long userId) {
        User user = userRepo.findById(userId).orElseThrow(EntityExistsException::new);
        return conversationRepo.findByParticipantsContains(user);
    }

    @Override
    public Conversation save(Conversation conversation) {
        return conversationRepo.save(conversation);
    }

    @Override
    public Conversation update(Conversation conversation, long id) {
        Conversation currentConversation = conversationRepo.findById(id).orElseThrow(EntityExistsException::new);
        for(Field f: conversation.getClass().getDeclaredFields()){
            f.setAccessible(true);
            try{
                if(f.get(conversation) != null){
                    f.set(currentConversation.getClass().getField(f.getName()), f);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return conversationRepo.save(currentConversation);
    }

    @Override
    public void delete(long id) {
        conversationRepo.deleteById(id);
    }
}
