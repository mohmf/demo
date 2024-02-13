package com.example.discount.repository;

import com.example.discount.model.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BillRepository  extends MongoRepository<Bill, String> {
}
