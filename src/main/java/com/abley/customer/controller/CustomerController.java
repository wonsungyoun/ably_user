package com.abley.customer.controller;

import com.abley.customer.dto.RegisterData;
import com.abley.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping ("/register")
    public ResponseEntity register(RegisterData registerData) {

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/customer/register").toUriString());

        return ResponseEntity.created(uri).body(customerService.register(registerData));
    }


    public void login() {

    }
}
