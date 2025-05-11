package com.example.coursemanagementsystem.service;

import com.example.coursemanagementsystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author legion
* @description 针对表【user(用户/user)】的数据库操作Service
* @createDate 2025-03-24 13:11:56
*/
public interface UserService extends IService<User> {


    User getLoginUser() ;

    long userRegister(String userAccount, String userPassword, String checkPassword, String userRole);

    User userLogin(String userAccount, String userPassword);

    boolean userLogout();
    boolean changePassword(Long userId , String originalPassword , String newPassword);

    Boolean changeRole(Long id, String userRole);

    List<User> getAllUsers();
}
