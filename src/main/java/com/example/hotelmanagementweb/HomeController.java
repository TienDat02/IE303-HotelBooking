package com.example.hotelmanagementweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "index";
    }
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    @RequestMapping("/about")
    public String about() {
        return "redirect:/about";
    }
    @RequestMapping("/booking")
    public String booking() {
        return "redirect:/booking";
    }
    @RequestMapping("/contact")
    public String contact() {
        return "redirect:/contact";
    }
    @RequestMapping("/room")
    public String room() {
        return "redirect:/room";
    }
    @RequestMapping("/service")
    public String service() {
        return "redirect:/service";
    }
    @RequestMapping("/team")
    public String team() {
        return "redirect:/team";
    }
    @RequestMapping("/testimonial")
    public String testimonial() {
        return "redirect:/testimonial";
    }
}