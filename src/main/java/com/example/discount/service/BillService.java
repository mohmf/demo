package com.example.discount.service;

import com.example.discount.model.Bill;

public interface BillService {
    Bill create(Bill bill);
    Double applyDiscount(String billId);

}
