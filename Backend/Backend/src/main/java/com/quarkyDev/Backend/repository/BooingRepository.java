package com.quarkyDev.Backend.repository;

import com.quarkyDev.Backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByRoomId(Long roomId);
    List<Booking> findByBookingConfirmationNumber(String bookingConfirmationNumber);

    List<Booking> findByUserId(Long userId);

}
