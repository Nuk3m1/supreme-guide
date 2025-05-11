package com.example.coursemanagementsystem.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChangePasswordRequest implements Serializable {
    /**
     * 原有密码
     */
    private String originalPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认密码
     */
    private String checkPassword;
}
