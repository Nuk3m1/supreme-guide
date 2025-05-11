package com.example.coursemanagementsystem.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.example.coursemanagementsystem.common.BaseResponse;
import com.example.coursemanagementsystem.common.ResultUtils;
import com.example.coursemanagementsystem.constant.UserConstant;
import com.example.coursemanagementsystem.entity.*;
import com.example.coursemanagementsystem.exception.CourseException;
import com.example.coursemanagementsystem.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.coursemanagementsystem.enums.ErrorCode;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private UserService userService;



    /**
     * 用户注册
     *
     * @param userregister 用户注册请求体
     * @return 用户id
     */
    @PostMapping("/register")
    public Long userRegister(UserRegister userregister) {
        if (userregister == null) {
            throw new CourseException(ErrorCode.PARAM_IS_WRONG);
        }
        String userAccount = userregister.getUserAccount();
        String userPassword = userregister.getUserPassword();
        String checkPassword = userregister.getCheckPassword();
        String userRole = userregister.getUserRole();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, userRole)) {
            throw new CourseException(ErrorCode.PARAM_IS_WRONG, "Params can not be empty!");
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, userRole);
        return result;
    }

    @PostMapping("/login")
    public User userLogin(UserLogin userLogin){
        if (userLogin == null){
            throw new CourseException(ErrorCode.PARAM_IS_WRONG);
        }
        String UserAccount = userLogin.getUserAccount();
        String UserPassword = userLogin.getUserPassword();
        if (StringUtils.isAnyBlank(UserAccount, UserPassword)) {
            throw new CourseException(ErrorCode.PARAM_IS_WRONG);
        }
        User loginUser = userService.userLogin(UserAccount, UserPassword);
        return loginUser;

    }
    @PostMapping("/logout")
    public Boolean userLogout() {
        boolean result = userService.userLogout();
        return result;
    }

    @PostMapping("/{userId}/password/change")
    public Boolean changePassword(@PathVariable Long userId,@RequestBody ChangePasswordRequest changePasswordRequest){
        if(changePasswordRequest == null){
            throw new CourseException(ErrorCode.PERMISSION_ERROR);
        }
        String originalPassword = changePasswordRequest.getOriginalPassword();
        String newPassword = changePasswordRequest.getNewPassword();
        String checkPassword = changePasswordRequest.getCheckPassword();
        // 存在空数据
        if (StringUtils.isAnyBlank(originalPassword, newPassword, checkPassword)) {
            throw new CourseException(ErrorCode.PARAM_IS_WRONG, "Params can not be empty!");
        }
        // 两次输入的密码不一致
        if (!newPassword.equals(checkPassword)) {
            throw new CourseException(ErrorCode.PARAM_IS_WRONG, "two passwords do not match!");
        }
//        User user = userService.getUser(userId);
        boolean result = userService.changePassword(userId, originalPassword, newPassword);
        if(!result){
            throw new CourseException(ErrorCode.OPERATION_ERROR);
        }
        return result;
    }
    @PostMapping("/admin/changeRole")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public boolean changeRole(@RequestBody ChangeRoleRequest changeRoleRequest){
        if(changeRoleRequest == null
                ||
                changeRoleRequest.getId() == null
                ||
                changeRoleRequest.getId() <= 0 ){
            throw new CourseException(ErrorCode.PARAM_IS_WRONG);
        }
        Boolean result = userService.changeRole(changeRoleRequest.getId(),changeRoleRequest.getUserRole());
        return result;
    }
    @GetMapping
    public List<User> getUsers() {

        List<User> users = userService.getAllUsers();
        return users;


    }

}
