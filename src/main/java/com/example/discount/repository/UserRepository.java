package com.example.discount.repository;

import com.example.discount.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository  extends MongoRepository<User, String> {
}
