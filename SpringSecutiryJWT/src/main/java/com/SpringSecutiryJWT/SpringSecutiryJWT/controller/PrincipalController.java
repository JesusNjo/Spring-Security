package com.SpringSecutiryJWT.SpringSecutiryJWT.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PrincipalController {


    @GetMapping("/hello")
    public String hello(){
        return "Hello world not secured";
    }

    @GetMapping("/helloSecured")
    public String helloS(){
        return "Hello world secured";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(){

        return ResponseEntity.ok(List.of());
    }
}
