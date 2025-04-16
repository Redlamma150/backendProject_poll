package com.example.backendProject.model;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "${external-api.backenduser.name}", url = "${external-api.backenduser.url}")
public interface UserClient {

    @GetMapping("/users/{id}")
    User getUserById(@PathVariable int id);

    @GetMapping("/users")
    List<User> getAllUsers();

}