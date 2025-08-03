package com.quarkyDev.Backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "email name is required")
    private String email;
    @NotBlank(message = "password is required")
    private String password;

}
