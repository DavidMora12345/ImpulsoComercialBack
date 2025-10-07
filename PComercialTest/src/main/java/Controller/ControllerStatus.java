package com.PComercial.PComercialTest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerStatus {

    @GetMapping("/api/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("OK!");
    }

}
