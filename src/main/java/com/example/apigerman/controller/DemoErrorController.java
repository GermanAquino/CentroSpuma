package com.example.apigerman.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoErrorController {

    @GetMapping("/force-500")
    public String forceError() {
        throw new RuntimeException("Error 500 provocado a propósito para demostrar manejo de excepciones.");
    }
}

