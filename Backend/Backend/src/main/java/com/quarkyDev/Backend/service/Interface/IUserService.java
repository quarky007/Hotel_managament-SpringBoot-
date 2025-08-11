package com.quarkyDev.Backend.service.Interface;

import com.quarkyDev.Backend.dto.LoginRequest;
import com.quarkyDev.Backend.dto.Response;
import com.quarkyDev.Backend.entity.User;

public interface IUserService {
    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

}