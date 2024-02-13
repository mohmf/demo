package com.example.discount.controller;

import com.example.discount.model.User;
import com.example.discount.repository.UserRepository;
import com.example.discount.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/{userId}")
    public User create(@PathVariable String userId) {
        return userService.findById(userId);
    }
}