package com.grocery.service;

import com.grocery.dto.User;
import com.grocery.exception.BadRequestException;
import com.grocery.payload.response.UserLoginResponse;

public interface AuthService {
    public UserLoginResponse loginHandler(String email, String password) throws BadRequestException;

    public User getUserLoggedIn();

    public String userRegister(User userRegisterRequest) throws BadRequestException ;
}
