package com.titsquad.SnakePractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/snake")
public class SnakeWebController {

    @GetMapping
    public String snake() {
        return "snake";
    }
}