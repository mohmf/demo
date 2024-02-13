package com.example.discount.model;

import com.example.discount.enums.UserType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record User(
        @Id String id,
        String name,
        UserType userType,
        int durationInYears
) {}
