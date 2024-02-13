package com.example.discount.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Bill(
        @Id String id,
        String userId,
        Double totalAmount,
        boolean isGrocery
) {}
