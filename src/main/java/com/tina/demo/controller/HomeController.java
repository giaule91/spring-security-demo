package com.tina.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
@RequestMapping(value = "/home")
@RestController
public class HomeController {

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to my api";
    }


}
