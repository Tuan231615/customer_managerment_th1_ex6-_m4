package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import com.codegym.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerResController {
    @Autowired
    private ICustomerService customerService;
    @GetMapping("findById/{id}")
    public Customer findById(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        return customer;
    }
    @PostMapping("/customer")
    public Customer save(@RequestBody Customer customer){
        customerService.save(customer);
        return customer;
    }
}
