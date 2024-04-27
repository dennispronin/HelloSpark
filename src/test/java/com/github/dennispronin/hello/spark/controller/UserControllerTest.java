package com.github.dennispronin.hello.spark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dennispronin.hello.spark.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.github.dennispronin.hello.spark.HelloSpark.startServer;
import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO Have not finished writing tests here
class UserControllerTest {

    private HttpURLConnection connection;
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() throws IOException, InterruptedException {
        startServer();
        Thread.sleep(3000);
        URL url = new URL("http://localhost:4567/user");
        this.connection = (HttpURLConnection) url.openConnection();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void should_get_all_users() throws IOException {
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        List<User> users = objectMapper.readValue(response.toString(), List.class);
        System.out.println(users);
        assertEquals(3, users.size());
    }
}