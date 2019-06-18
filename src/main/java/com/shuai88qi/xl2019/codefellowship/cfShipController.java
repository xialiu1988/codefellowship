package com.shuai88qi.xl2019.codefellowship;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class cfShipController {
    @GetMapping("/")
    public String getHomePage(){
        return "home";
    }
}
