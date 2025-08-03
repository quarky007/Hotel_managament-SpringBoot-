package com.quarkyDev.Backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "checkin date is required")
    private LocalDate checkInDate;
    @Future(message = "checkout out date must be in future ")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "number of adults must be greater than 0")
    private int numOfAdults;

    @Min(value = 0, message = "number of children must be greater than or equal to 0")
    private int numOfChildren;


    private int totalNoOfGuests;


    private String bookingConfirmationNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id" , referencedColumnName = "id")

    private User user ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id" , referencedColumnName = "id")
    private Room room;

    public void setTotalNoOfGuests() {
        this.totalNoOfGuests = this.numOfAdults + this.numOfChildren;
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
        setTotalNoOfGuests();
    }

    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
        setTotalNoOfGuests();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numOfAdults=" + numOfAdults +
                ", numOfChildren=" + numOfChildren +
                ", totalNoOfGuests=" + totalNoOfGuests +
                ", bookingConfirmationNumber='" + bookingConfirmationNumber + '\'' +
                '}';
    }
}

