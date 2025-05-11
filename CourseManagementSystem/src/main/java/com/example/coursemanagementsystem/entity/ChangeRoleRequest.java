package com.example.coursemanagementsystem.entity;

import cn.dev33.satoken.annotation.SaCheckRole;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ChangeRoleRequest implements Serializable {


    private Long id;
    private String userRole;





}
