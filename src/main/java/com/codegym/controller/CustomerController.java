package com.codegym.controller;

import com.codegym.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.codegym.service.ICustomerService;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/create-customer")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("create-customer")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "create new customer successfully!");
        return modelAndView;
    }

    @GetMapping("/customers")
    public ModelAndView listCustomer() {
        List<Customer> customers = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customer", customers);
        return modelAndView;
    }

    @GetMapping("/edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        RestTemplate restTemplate = new RestTemplate();
        Customer customer = restTemplate.getForObject("http://localhost:8080/findById/" + id, Customer.class);
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @PostMapping("/edit-customer")
    public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("redirect:/customers");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("message", "edit customer successfully!");
        return modelAndView;
    }

    @GetMapping("/delete-customer/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/customer/delete");
        RestTemplate restTemplate = new RestTemplate();
        Customer customer = restTemplate.getForObject("http://localhost:8080/findById/" + id, Customer.class);
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("message", "delete customer successfully!");
        return modelAndView;
    }

    @PostMapping("/delete-customer")
    public String deleteForm(@ModelAttribute("customer") Customer customer) {
        customerService.remove(customer.getId());
        return "redirect:customers";
    }
}
