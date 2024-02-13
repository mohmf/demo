package com.example.discount.service;

import com.example.discount.model.User;

public interface UserService {
    User create(User user);
    User findById(String id);
}
