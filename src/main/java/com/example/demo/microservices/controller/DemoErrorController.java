package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoErrorController {

    @GetMapping("/force-500")
    public String forceError() {
        log.error("Lanzando error 500 provocado a propósito");
        throw new RuntimeException("Error 500 provocado a propósito para demostrar manejo de excepciones.");
    }
}
