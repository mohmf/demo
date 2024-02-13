package com.example.discount;

import com.example.discount.enums.UserType;
import com.example.discount.model.Bill;
import com.example.discount.model.User;
import com.example.discount.repository.BillRepository;
import com.example.discount.repository.UserRepository;
import com.example.discount.service.impl.BillServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BillServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BillRepository billRepository;

    @InjectMocks
    private BillServiceImpl billService;

    @Test
    public void when_employee_apply_30_per_discount() {
        double amountToTest = 300.0;

        User employee = new User("user1", "Mohammed", UserType.EMPLOYEE, 0);
        Bill bill = new Bill("bill1", employee.id(), amountToTest, false);

        when(billRepository.findById("bill1")).thenReturn(Optional.of(bill));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(employee));

        Double payableAmount = billService.applyDiscount("bill1");
        Double expectedDiscount = amountToTest * 0.30;
        Double expectedAmount = amountToTest - expectedDiscount - ((int) (amountToTest / 100) * 5);

        assertThat(payableAmount).isEqualTo(expectedAmount);
    }

    @Test
    public void when_affiliate_apply_10_per_discount() {
        double amountToTest = 300.0;

        User employee = new User("user1", "Mohammed", UserType.AFFILIATE, 0);
        Bill bill = new Bill("bill2", employee.id(), amountToTest, false);

        when(billRepository.findById("bill2")).thenReturn(Optional.of(bill));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(employee));

        Double payableAmount = billService.applyDiscount("bill2");
        Double expectedDiscount = amountToTest * 0.10;
        Double expectedAmount = amountToTest - expectedDiscount - ((int) (amountToTest / 100) * 5);

        assertThat(payableAmount).isEqualTo(expectedAmount);
    }

    @Test
    public void when_customer_over_2years_apply_5_per_discount() {
        double amountToTest = 300.0;

        User longTermCustomer = new User("user1", "Mohammed", UserType.CUSTOMER, 3);
        Bill bill = new Bill("bill3", longTermCustomer.id(), amountToTest, false);


        when(billRepository.findById("bill3")).thenReturn(Optional.of(bill));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(longTermCustomer));

        Double payableAmount = billService.applyDiscount("bill3");
        Double expectedDiscount = amountToTest * 0.05;
        Double expectedAmount = amountToTest - expectedDiscount - ((int) (amountToTest / 100) * 5);

        assertThat(payableAmount).isEqualTo(expectedAmount);
    }

    @Test
    public void when_bill_for_groceries_then_no_discount() {
        double amountToTest = 300.0;

        User customer = new User("user1", "Mohammed", UserType.CUSTOMER, 1);
        Bill bill = new Bill("bill4", customer.id(), amountToTest, true);


        when(billRepository.findById("bill4")).thenReturn(Optional.of(bill));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(customer));

        Double payableAmount = billService.applyDiscount("bill4");
        Double expectedDiscount = 0.0; // No discount
        Double expectedAmount = amountToTest - expectedDiscount - ((int) (amountToTest / 100) * 5);

        assertThat(payableAmount).isEqualTo(expectedAmount);
    }

    @Test
    public void when_bill_exceeds_100_apply_5_discount_for_every_100() {
        double amountToTest = 990;

        User customer = new User("user1", "Mohammed", UserType.CUSTOMER, 1);
        Bill bill = new Bill("bill5", customer.id(), amountToTest, true);


        when(billRepository.findById("bill5")).thenReturn(Optional.of(bill));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(customer));

        Double payableAmount = billService.applyDiscount("bill5");
        int expectedDiscount = ((int) (amountToTest / 100)) * 5;
        Double expectedAmount = amountToTest - expectedDiscount;

        assertThat(payableAmount).isEqualTo(expectedAmount);
    }



}
