package com.jakeesveld.hmlserver.service;

import com.jakeesveld.hmlserver.model.User;
import com.jakeesveld.hmlserver.model.UserRoles;
import com.jakeesveld.hmlserver.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User save(User user) {
        User newUser = new User(user.getUsername(), user.getPassword(), user.getEmail());

        List<UserRoles> userRoles = new ArrayList<>();

        for (UserRoles ur: user.getUserRoles()){
            userRoles.add(new UserRoles(newUser, ur.getRole()));
        }

        newUser.setUserRoles(userRoles);
        return userRepo.save(user);
    }

    @Override
    public void delete(long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) userRepo.delete(user.get());
    }

    @Override
    public User update(User user, long id) {
        Optional<User> currentUser = userRepo.findById(id);
        for(Field f: user.getClass().getDeclaredFields()){
            f.setAccessible(true);
            try {
                if (f.get(user) == null && currentUser.isPresent()) {
                    f.set(user, currentUser.get().getClass().getField(f.getName()));
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(s);
        if (user.isEmpty())
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), new ArrayList<SimpleGrantedAuthority>());    }
}
