package com.jakeesveld.hmlserver.api;

import com.jakeesveld.hmlserver.model.User;
import com.jakeesveld.hmlserver.service.ConversationService;
import com.jakeesveld.hmlserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;

@RestController
public class ConversationController {

    private final ConversationService conversationService;
    private final UserService userService;

    @Autowired
    public ConversationController(ConversationService conversationService, UserService userService) {
        this.conversationService = conversationService;
        this.userService = userService;
    }


    @GetMapping("/conversations/getall")
    public ResponseEntity<?> getAllConversations(Authentication authentication){
        User user = getUserFromAuth(authentication);
        return new ResponseEntity<>(conversationService.findAllByUser(user.getId()), HttpStatus.OK);
    }


    @DeleteMapping("/conversations/{id}/delete")
    public ResponseEntity<?> deleteConversation(@PathVariable long id){
        conversationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    private User getUserFromAuth(Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return userService.findByUsername(user.getUsername()).orElseThrow(EntityExistsException::new);
    }
}
