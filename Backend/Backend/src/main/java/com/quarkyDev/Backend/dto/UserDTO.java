package com.quarkyDev.Backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quarkyDev.Backend.entity.Booking;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;
    private String user;
    private String email;
    private String phoneNumber;
    private String role;
    private List<Booking> bookings= new ArrayList<>();




}
