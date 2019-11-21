package com.jakeesveld.hmlserver.api;

import com.jakeesveld.hmlserver.model.User;
import com.jakeesveld.hmlserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

    private final UserService userService;
    private final TokenStore tokenStore;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthController(@Lazy TokenStore tokenStore,
                          UserService userService,
                          @Lazy AuthenticationManager authenticationManager,
                          @Lazy PasswordEncoder encoder) {
        this.tokenStore = tokenStore;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> handleSignup(@RequestBody User user, HttpServletRequest request){
        userService.save(user);
        UsernamePasswordAuthenticationToken token = getToken(user.getUsername(), encoder.encode(user.getPassword()), request);
        if(token != null) {
            SecurityContextHolder.getContext().setAuthentication(token);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }else{
            throw new AuthenticationServiceException("Failed to create user.");
        }
    }

    private UsernamePasswordAuthenticationToken getToken(String username, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        token.setDetails(new WebAuthenticationDetails(request));
        authenticationManager.authenticate(token);

        if(token.isAuthenticated()){
            return token;
        }
        return null;
    }
}
