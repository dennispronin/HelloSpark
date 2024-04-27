package com.github.dennispronin.hello.spark.repository;

import com.github.dennispronin.hello.spark.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository = new UserRepository(new HashMap<>());
    }

    @Test
    void should_get_user() {
        User testUser = createTestUserInStorage();

        assertNotNull(userRepository.get(testUser.getUuid()));
    }

    @Test
    void should_get_all_users() {
        for (int i = 0; i < 3; i++) {
            createTestUserInStorage();
        }

        assertEquals(3, userRepository.getAll().size());
    }

    @Test
    void should_delete_user() {
        User testUser = createTestUserInStorage();

        assertNotNull(userRepository.get(testUser.getUuid()));

        userRepository.delete(testUser.getUuid());

        assertNull(userRepository.get(testUser.getUuid()));
    }

    @Test
    void should_create_user() {
        User createdUser = createTestUserInStorage();

        assertNotNull(createdUser);
        assertNotNull(createdUser.getUuid());
    }

    @Test
    void should_create_user_given_id_ignored() {
        String customUuid = "my custom uuid";
        User user = new User(customUuid, "Denis", 27);

        User createdUser = userRepository.create(user);

        assertNotNull(createdUser);
        assertNotEquals(customUuid, createdUser.getUuid());
    }

    @Test
    void should_update_user() {
        User testUser = createTestUserInStorage();
        String nameBeforeUpdate = testUser.getName();
        testUser.setName("NewName");

        User updatedUser = userRepository.update(testUser);

        assertNotEquals(updatedUser.getName(), nameBeforeUpdate);
    }

    @Test
    void should_not_update_non_existent_user() {
        User testUser = createTestUserInStorage();
        testUser.setUuid("my custom uuid");

        User updatedUser = userRepository.update(testUser);

        assertNull(updatedUser);
    }

    private User createTestUserInStorage() {
        User user = new User(null, "Denis", 27);
        return userRepository.create(user);
    }
}