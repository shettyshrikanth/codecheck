package com.hsbc.codecheck.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelathCheckController {

    @GetMapping("/health")
    boolean health() {
        return true;
    }
}
