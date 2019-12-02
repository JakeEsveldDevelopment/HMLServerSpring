package com.jakeesveld.hmlserver.api;

import com.jakeesveld.hmlserver.model.User;
import com.jakeesveld.hmlserver.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/getuser")
    public ResponseEntity<?> getUser(Authentication authentication){
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping(value = "/getuser/byname/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable String name){
        User user = userService.findByUsernameContaining(name).orElseThrow(EntityNotFoundException::new);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/update/me")
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody User user){
        return new ResponseEntity<>(userService.update(user, user.getId()), HttpStatus.OK);
    }
}
