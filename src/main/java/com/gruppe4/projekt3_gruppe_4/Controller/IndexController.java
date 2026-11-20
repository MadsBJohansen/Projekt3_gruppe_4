package com.gruppe4.projekt3_gruppe_4.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String showIndex(){
        return "index";
    }
}
