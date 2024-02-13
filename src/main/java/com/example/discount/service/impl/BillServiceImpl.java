package com.example.discount.service.impl;

import com.example.discount.model.Bill;
import com.example.discount.model.User;
import com.example.discount.repository.BillRepository;
import com.example.discount.repository.UserRepository;
import com.example.discount.service.BillService;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {
    private static final double EMPLOYEE_DISCOUNT = 0.30;
    private static final double AFFILIATE_DISCOUNT = 0.10;;
    private static final double CUSTOMER_DISCOUNT = 0.05;
    private static final double EVERY100_DISCOUNT = 5;
    private final UserRepository userRepository;
    private final BillRepository billRepository;

    public BillServiceImpl(UserRepository userRepository, BillRepository billRepository) {
        this.userRepository = userRepository;
        this.billRepository = billRepository;
    }

    @Override
    public Bill create(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public Double applyDiscount(String billId) {
        Bill bill = billRepository.findById(billId).orElseThrow();
        User user = userRepository.findById(bill.userId()).orElseThrow();
        double discount = 0;

        if (!bill.isGrocery()) {
            discount = switch (user.userType()) {
                case EMPLOYEE -> bill.totalAmount() * EMPLOYEE_DISCOUNT;
                case AFFILIATE -> bill.totalAmount() * AFFILIATE_DISCOUNT;
                case CUSTOMER -> bill.totalAmount() * CUSTOMER_DISCOUNT;
            };
        }

        //For every 100 discount
        discount = discount + ((int) (bill.totalAmount() / 100) * EVERY100_DISCOUNT);

        return bill.totalAmount() - discount;
    }
}
