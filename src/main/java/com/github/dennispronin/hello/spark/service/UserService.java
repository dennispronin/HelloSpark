package com.github.dennispronin.hello.spark.service;

import com.github.dennispronin.hello.spark.model.User;
import com.github.dennispronin.hello.spark.repository.UserRepository;

import java.util.List;

public class UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with id=%s not found";
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User get(String uuid) {
        return userRepository.get(uuid);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public void delete(String uuid) {
        User existingUser = get(uuid);
        if (existingUser == null) {
            throw new RuntimeException(USER_NOT_FOUND_MESSAGE.formatted(uuid));
        }
        userRepository.delete(uuid);
    }

    public User create(User user) {
        return userRepository.create(user);
    }

    public User update(User user, String uuid) {
        User existingUser = get(uuid);
        if (existingUser == null) {
            throw new RuntimeException(USER_NOT_FOUND_MESSAGE.formatted(uuid));
        }
        user.setUuid(uuid);
        return userRepository.update(user);
    }
}