package com.payment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("payment/")
public class PaymentController {

    @Autowired
    private PaymentRepository repository;

    @GetMapping("all")
    public List<Payment> getAllPayments(){
        return repository.findAll();
    }
}
