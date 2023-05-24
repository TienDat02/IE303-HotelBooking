package com.example.hotelmanagementweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class HotelManagementWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelManagementWebApplication.class, args);
    }

}
