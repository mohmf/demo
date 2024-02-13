package com.example.discount.controller;

import com.example.discount.model.Bill;
import com.example.discount.service.BillService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/{billId}")
    public Double calculateDiscount(@PathVariable String billId) {
        return billService.applyDiscount(billId);
    }

    @PostMapping
    public Bill create(@RequestBody Bill bill) {
        Bill result = billService.create(bill);
        return result;
    }
}