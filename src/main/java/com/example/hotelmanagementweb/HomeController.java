package com.example.hotelmanagementweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "index";
    }
    @RequestMapping("/about")
    public String about() {
        return "redirect:/about";
    }
    @RequestMapping("/booking")
    public String booking() { return "redirect:/booking"; }
}