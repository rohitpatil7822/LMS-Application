package com.example.lms.controller;


import com.example.lms.service.EasterEggService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EasterEggController {

    @Autowired
    private EasterEggService easterEggService;

    @GetMapping("/hidden-feature/{number}")
    public ResponseEntity<String> getNumberFact(@PathVariable int number) {
        String fact = easterEggService.getNumberFact(number);
        return ResponseEntity.ok(fact);
    }
}