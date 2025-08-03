package com.quarkyDev.Backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookingConfirmationNumber;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoURL;
    private String roomDescription;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", bookingConfirmationNumber='" + bookingConfirmationNumber + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomPrice='" + roomPrice + '\'' +
                ", roomPhotoURL='" + roomPhotoURL + '\'' +
                ", roomDescription='" + roomDescription + '\'' +
                '}';
    }
}
