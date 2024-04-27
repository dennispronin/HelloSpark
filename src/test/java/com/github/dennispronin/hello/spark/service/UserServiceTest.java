package com.github.dennispronin.hello.spark.service;

import com.github.dennispronin.hello.spark.model.User;
import com.github.dennispronin.hello.spark.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void init() {
        userService = new UserService(new UserRepository(new HashMap<>()));
    }

    @Test
    void should_get_user() {
        User testUser = createTestUserInStorage();

        assertNotNull(userService.get(testUser.getUuid()));
    }

    @Test
    void should_get_all_users() {
        for (int i = 0; i < 3; i++) {
            createTestUserInStorage();
        }

        assertEquals(3, userService.getAll().size());
    }

    @Test
    void should_delete_user() {
        User testUser = createTestUserInStorage();

        assertNotNull(userService.get(testUser.getUuid()));

        userService.delete(testUser.getUuid());

        assertNull(userService.get(testUser.getUuid()));
    }

    @Test
    void should_not_delete_non_existent_user() {
        assertThrows(RuntimeException.class, () -> userService.delete("nonExistentUuid"));
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

        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertNotEquals(customUuid, createdUser.getUuid());
    }

    @Test
    void should_update_user() {
        User testUser = createTestUserInStorage();
        String nameBeforeUpdate = testUser.getName();
        testUser.setName("NewName");

        User updatedUser = userService.update(testUser, testUser.getUuid());

        assertNotEquals(updatedUser.getName(), nameBeforeUpdate);
    }

    @Test
    void should_not_update_user_by_non_existent_uuid() {
        User testUser = createTestUserInStorage();

        assertThrows(RuntimeException.class, () -> userService.update(testUser, "nonExistentUuid"));
    }

    @Test
    void should_not_update_existent_user_id() {
        User testUser = createTestUserInStorage();
        String nameBeforeUpdate = testUser.getName();
        String testUserUuid = testUser.getUuid();
        String customUuid = "my custom uuid";
        testUser.setUuid(customUuid);
        testUser.setName("NewName");

        User updatedUser = userService.update(testUser, testUserUuid);

        assertNotNull(updatedUser);
        assertNotEquals(updatedUser.getUuid(), customUuid);
        assertNotEquals(updatedUser.getName(), nameBeforeUpdate);
    }

    private User createTestUserInStorage() {
        User user = new User(null, "Denis", 27);
        return userService.create(user);
    }
}