package com.jakeesveld.hmlserver.service;

import com.jakeesveld.hmlserver.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);

    User save(User user);

    void delete(long id);

    User update(User user, long id);


}
