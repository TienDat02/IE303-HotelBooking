package com.example.hotelmanagementweb;

public class Room {
    private int roomNumber;
    private String roomType;
    private String roomStatus;
    private Float roomPrice;

    public Room(int roomNumber, String roomType, String roomStatus, Float roomPrice) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
        this.roomPrice = roomPrice;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public Float getRoomPrice() {
        return roomPrice;
    }
}
