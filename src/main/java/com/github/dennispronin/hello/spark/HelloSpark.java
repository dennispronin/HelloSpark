package com.github.dennispronin.hello.spark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dennispronin.hello.spark.controller.UserController;
import com.github.dennispronin.hello.spark.model.User;
import com.github.dennispronin.hello.spark.repository.UserRepository;
import com.github.dennispronin.hello.spark.service.UserService;

import java.util.HashMap;
import java.util.List;

import static com.github.dennispronin.hello.spark.Paths.USER_PATH;
import static com.github.dennispronin.hello.spark.Paths.UUID_PATH;
import static spark.Spark.*;


public class HelloSpark {

    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        threadPool(50);
        UserController userController = new UserController(new UserService(buildRepository()), new ObjectMapper());
        mapUserApiPaths(userController);
    }

    private static UserRepository buildRepository() {
        UserRepository userRepository = new UserRepository(new HashMap<>());
        initRepository(userRepository);
        return userRepository;
    }

    private static void initRepository(UserRepository userRepository) {
        List<User> users = List.of(
                userRepository.create(new User(null, "Denis", 27)),
                userRepository.create(new User(null, "Anna", 26)),
                userRepository.create(new User(null, "Toby", 4))
        );
        System.out.println("Test users:\n");
        System.out.println(users);
    }

    private static void mapUserApiPaths(UserController userController) {
        get(USER_PATH + UUID_PATH, userController.getUserRoute());
        get(USER_PATH, userController.getAllUserRoute());
        delete(USER_PATH + UUID_PATH, userController.deleteUserRoute());
        put(USER_PATH + UUID_PATH, userController.updateUserRoute());
        post(USER_PATH, userController.createUserRoute());
    }
}