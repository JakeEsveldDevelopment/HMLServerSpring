package com.jakeesveld.hmlserver.service;

import com.jakeesveld.hmlserver.model.User;
import com.jakeesveld.hmlserver.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Optional;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

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


}
