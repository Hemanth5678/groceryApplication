package com.grocery.payload.response;


import java.io.Serializable;
import java.util.Date;

import com.grocery.enums.AccountStatus;
import com.grocery.enums.Role;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserLoginResponse implements Serializable{
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private Role role;
    private AccountStatus status;
    private Date createdAt;
    private Date updatedAt;
    private String accessToken;
    private String refreshToken;
}
