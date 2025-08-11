package com.quarkyDev.Backend.service.Interface;

import com.quarkyDev.Backend.dto.Response;
import com.quarkyDev.Backend.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);

}