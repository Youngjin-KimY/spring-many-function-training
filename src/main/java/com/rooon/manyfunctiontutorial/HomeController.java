package com.rooon.manyfunctiontutorial;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/test")
    public Student test() throws InterruptedException {
        Thread.sleep(1000L);
        return new Student("yj",1L,  LocalDateTime.now());
    }
}
