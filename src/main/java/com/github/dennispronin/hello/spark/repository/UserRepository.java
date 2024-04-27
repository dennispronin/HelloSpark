package com.github.dennispronin.hello.spark.repository;

import com.github.dennispronin.hello.spark.model.User;

import java.util.*;

public class UserRepository {

    private final Map<String, User> userStorage;

    public UserRepository(Map<String, User> userStorage) {
        this.userStorage = userStorage;
    }

    public User get(String uuid) {
        return userStorage.get(uuid);
    }

    public List<User> getAll() {
        return userStorage.values().stream().toList();
    }

    public void delete(String uuid) {
        userStorage.remove(uuid);
    }

    public User create(User user) {
        String uuid = generateUniqueUuid();
        user.setUuid(uuid);
        userStorage.put(uuid, user);
        return get(uuid);
    }

    public User update(User user) {
        User existingUser = get(user.getUuid());
        if (existingUser != null) {
            return userStorage.put(existingUser.getUuid(), existingUser);
        }
        return null;
    }

    private String generateUniqueUuid() {
        String uuid;

        do {
            uuid = Long.toString(UUID.randomUUID().getLeastSignificantBits());
        }
        while (userStorage.get(uuid) != null);

        return uuid;
    }
}