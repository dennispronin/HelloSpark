package com.github.dennispronin.hello.spark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dennispronin.hello.spark.model.User;
import com.github.dennispronin.hello.spark.service.UserService;
import spark.Route;

import java.util.List;

import static com.github.dennispronin.hello.spark.Paths.UUID_PARAM;

public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    public Route getUserRoute() {
        return (req, res) -> {
            User user = userService.get(req.params(UUID_PARAM));
            return objectMapper.writeValueAsString(user);
        };
    }

    public Route getAllUserRoute() {
        return (req, res) -> {
            List<User> users = userService.getAll();
            return objectMapper.writeValueAsString(users);
        };
    }

    public Route deleteUserRoute() {
        return (req, res) -> {
            userService.delete(req.params(UUID_PARAM));
            return res;
        };
    }

    public Route createUserRoute() {
        return (req, res) -> {
            User user = objectMapper.readValue(req.body(), User.class);
            return userService.create(user);
        };
    }

    public Route updateUserRoute() {
        return (req, res) -> {
            String uuid = req.params(UUID_PARAM);
            User user = objectMapper.readValue(req.body(), User.class);
            return userService.update(user, uuid);
        };
    }
}