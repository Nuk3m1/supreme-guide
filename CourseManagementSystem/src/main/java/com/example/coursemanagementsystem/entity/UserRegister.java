package com.example.coursemanagementsystem.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserRegister implements Serializable {
    private String userAccount;
    private String userPassword;

    private String checkPassword;

    private String userRole;
}
