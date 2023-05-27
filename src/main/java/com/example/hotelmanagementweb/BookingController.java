package com.example.hotelmanagementweb;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class BookingController {
    @GetMapping("/booking")
    /*
    public String showBookingPage() {
        return "booking";
    }
     */
    //take room data from database and display it on booking page and also render the booking page
    public String getRoom(Model model) {
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/hotelmanagement",
                "root",
                "");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM room WHERE Room_Status = 'Trống'";
        List<Room> rooms = jdbcTemplate.query(sql, (rs, rowNum) -> new Room(rs.getInt("Room_ID"), rs.getString("Room_Type"), rs.getString("Room_Status"), rs.getFloat("Room_Price")));
        model.addAttribute("rooms", rooms);

        return "booking";
    }
    @PostMapping("/booking")



    @ResponseBody
    public String processBookingForm(@RequestBody Map<String, Object> formData) {
        String name = (String) formData.get("name");
        String email = (String) formData.get("email");

        int adults = formData.get("select1") != null ? (Integer) formData.get("select1") : 0;
        int children = formData.get("select2") != null ? (Integer) formData.get("select2") : 0;
        int room = formData.get("select3") != null ? (Integer) formData.get("select3") : 0;
        String specialRequests = (String) formData.get("message");

        // Create a DataSource object to connect to the database
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/HotelManagement",
                "root",
                "tiendat1102");

        // Create a JdbcTemplate object using the DataSource
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Construct an SQL INSERT statement with placeholders for the form data
        String sql = "INSERT INTO bookings (name, email, adults, children, room, special_requests) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        // Execute the INSERT statement with the form data as arguments
        jdbcTemplate.update(sql, name, email, adults, children, room, specialRequests);

        // Return a JSON response with the retrieved form data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("name", name);
        responseData.put("email", email);

        responseData.put("adults", adults);
        responseData.put("children", children);
        responseData.put("room", room);
        responseData.put("specialRequests", specialRequests);
        return new Gson().toJson(responseData);
    }
}