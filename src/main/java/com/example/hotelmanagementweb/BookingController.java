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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public String getRoom(Model model, Map<String, Object> formData) {
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/hotelmanagement",
                "root",
                "123456");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        LocalDateTime checkin = (LocalDateTime) formData.get("checkin");
        LocalDateTime checkout = (LocalDateTime) formData.get("checkout");
        
        String sql;

        if (checkin == null || checkout == null) {
            sql = "SELECT * FROM room WHERE Room_Status = 'Trống'";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedCheckin = checkin.format(formatter);
            String formattedCheckout = checkout.format(formatter);
            sql = "SELECT * FROM room " +
            "JOIN reservation ON room.Room_ID = reservation.Room_ID " +
            "WHERE Room_Status = 'Trống' " +
            "AND ((Expected_Checkin_Date >= '" + formattedCheckin + "' AND Expected_Checkin_Date >= '" + formattedCheckout + "') " +
            "OR (Expected_Checkout_Date <= '" + formattedCheckin + "' AND Expected_Checkout_Date <= '" + formattedCheckout + "'))";
        }

        List<Room> rooms = jdbcTemplate.query(sql, (rs, rowNum) -> new Room(rs.getInt("Room_ID"), rs.getString("Room_Type"), rs.getString("Room_Status"), rs.getFloat("Room_Price")));
        model.addAttribute("rooms", rooms);

        return "booking";
    }
    @PostMapping("/booking")

    

    @ResponseBody
    public String processBookingForm(@RequestBody Map<String, Object> formData) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
        String name = (String) formData.get("name");
        String phone = (String) formData.get("phone");
        String cccd = (String) formData.get("cccd");
        LocalDateTime checkin = LocalDateTime.parse((String) formData.get("checkin"), formatter);
        LocalDateTime checkout = LocalDateTime.parse((String) formData.get("checkout"), formatter);
        int adults = Integer.parseInt((String) formData.get("adults"));
        int children = Integer.parseInt((String) formData.get("children"));
        int room = Integer.parseInt((String) formData.get("room"));
        LocalDateTime reserveDate = LocalDateTime.now();
        int numberOfGuest = adults + children;
        String status = "Đặt trước";
        String specialRequests = (String) formData.get("message");

        // Create a DataSource object to connect to the database
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/hotelmanagement",
                "root",
                "123456");

        // Create a JdbcTemplate object using the DataSource
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Check if the Guest_ID value exists in the guest table
        String sqlCheckGuest = "SELECT COUNT(*) FROM guest WHERE Guest_ID = ?";
        int count = jdbcTemplate.queryForObject(sqlCheckGuest, new Object[]{cccd}, Integer.class);

        // If the Guest_ID value does not exist in the guest table, insert it
        if (count == 0) {
            String sqlInsertGuest = "INSERT INTO guest (Guest_ID, Guest_name, Guest_Phone, Guest_Notes) " +
                    "VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sqlInsertGuest, cccd, name, phone, specialRequests);
        }

        // Construct an SQL INSERT statement with placeholders for the form data
        String sqlReservation = "INSERT INTO reservation (Guest_ID, Room_ID, Number_ofGuest, Expected_Checkin_Date, Expected_Checkout_Date, Reserve_Date, Reservation_Status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Execute the INSERT statement with the form data as arguments
        jdbcTemplate.update(sqlReservation, cccd, room, numberOfGuest, checkin, checkout, reserveDate, status);

        // Return a JSON response with the retrieved form data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("name", name);
        responseData.put("phone", phone);
        responseData.put("cccd", cccd);
        responseData.put("adults", adults);
        responseData.put("children", children);
        responseData.put("room", room);
        responseData.put("specialRequests", specialRequests);
        return new Gson().toJson(responseData);
    }
}