package com.github.mdutka5.simplifiedstockmarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChaosController {
    private final ApplicationContext applicationContext;

    @PostMapping("/chaos")
    public void chaos() {
        System.exit(SpringApplication.exit(applicationContext));
    }
}
