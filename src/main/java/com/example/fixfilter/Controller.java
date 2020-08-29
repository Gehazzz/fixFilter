package com.example.fixfilter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @PostMapping("/name")
    Person newPerson(@RequestBody Person person) {
        return person;
    }
}
