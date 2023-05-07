package com.stock.controllers;

import com.stock.repository.account.PaymentRepository;
import com.stock.repository.account.TransactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/user/transact")
public class TransactController {
    private final PaymentRepository paymentRepository;
    private final TransactRepository transactRepository;

    @Autowired
    public TransactController(PaymentRepository paymentRepository, TransactRepository transactRepository) {
        this.paymentRepository = paymentRepository;
        this.transactRepository = transactRepository;
    }
    @GetMapping("/deposit")
    public String getDepositPage(){

        return "deposit";
    }

    @PostMapping("/deposit")
    public String deposit(){

        return "redirect:/api/v1/user";
    }
}
