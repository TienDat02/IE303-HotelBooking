package com.example.hotelmanagementweb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class RoomController {
    @GetMapping("/room")
    public String roomPage() {
        return "room";
    }
}
