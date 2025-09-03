package com.pat.s1ac.infrastructure.rest.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/hello")
public class HelloRoute {
    private static final Logger log = LoggerFactory.getLogger(HelloRoute.class);

    @GetMapping
    public ResponseEntity<String> getProduct() {
        log.error("---HELLO");
        return ResponseEntity.ok("Test response");
    }
}
