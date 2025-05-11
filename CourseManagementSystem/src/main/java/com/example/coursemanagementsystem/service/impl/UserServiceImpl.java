package com.example.coursemanagementsystem.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.coursemanagementsystem.constant.UserConstant;
import com.example.coursemanagementsystem.entity.Course;
import com.example.coursemanagementsystem.entity.User;
import com.example.coursemanagementsystem.exception.CourseException;
import com.example.coursemanagementsystem.service.UserService;
import com.example.coursemanagementsystem.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.example.coursemanagementsystem.enums.ErrorCode;
import com.example.coursemanagementsystem.enums.UserRoleEnum;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* @author legion
* @description 针对表【user(用户/user)】的数据库操作Service实现
* @createDate 2025-03-24 13:11:56
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    /**
     * "information"  --- 盐值
     * 用于加密用户密码
     * Used to encrypt user passwords
     */
    public static final String SALT = "information";


    @Override
    public User getLoginUser() {
        //判断是否登录
        Object LoginUserId = StpUtil.getLoginIdDefaultNull();
        if(LoginUserId == null){
            throw new CourseException(ErrorCode.NOT_LOGIN_ERROR);
        }
        User user = this.baseMapper.selectById((String) LoginUserId);

        return user;
    }

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String userRole) {
        // 账户，密码和校验密码 三者不能为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new CourseException(ErrorCode.PARAM_IS_WRONG, "Parameter is empty!");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new CourseException(ErrorCode.PARAM_IS_WRONG, "The passwords entered do not align with one another!");
        }
        // 身份是否存在于枚举中
        if (!UserRoleEnum.getValues().contains(userRole)) {
            throw new CourseException(ErrorCode.PARAM_IS_WRONG, "The user role is invalid!");
        }
        synchronized (userAccount.intern()){
            // 查询
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_account", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            if ( count > 0) {
                System.out.println("该账号已存在");
                throw new CourseException(ErrorCode.PARAM_IS_WRONG, "Account already exists!");
            }
            //  加密密码
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            String userName = "user_" + RandomUtil.randomString(6);
            //  插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            user.setUserName(userName);
            user.setUserRole(userRole);


            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new CourseException(ErrorCode.SYSTEM_ERROR, "Registration Error");
            }
            return user.getId();

        }
    }

    @Override
    public User userLogin(String userAccount, String userPassword) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new CourseException(ErrorCode.PARAM_IS_WRONG, "参数为空");
        }

        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        User user = this.baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUserAccount, userAccount)
                .eq(User::getUserPassword, encryptPassword));
        // 用户不存在
        if (user == null) {
            throw new CourseException(ErrorCode.PARAM_IS_WRONG, "用户不存在或密码错误");
        }


        StpUtil.login(user.getId());


        StpUtil.getSession().set("user_login", user);



        return user;
    }

    @Override
    public boolean userLogout() {
        StpUtil.checkLogin();
        // 移除登陆态
        StpUtil.logout();
        return true;
    }



    @Override
    public boolean changePassword(Long userId , String originalPassword , String newPassword) {
        User user = baseMapper.selectById(userId);
        System.out.println(userId);
        if(user == null ){
            throw(new CourseException(ErrorCode.NOT_FOUND_ERROR,"user not found"));
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + originalPassword).getBytes());
        // 验证原有密码
        if (!user.getUserPassword().equals(encryptPassword)) {

            throw new CourseException(ErrorCode.PARAM_IS_WRONG, "Original password is incorrect!");
        }

        // 加密新密码
        String encodedNewPassword = DigestUtils.md5DigestAsHex((SALT + newPassword).getBytes());
        user.setUserPassword(encodedNewPassword);

        // 更新用户密码
        int updateCount = baseMapper.updateById(user);
        return updateCount > 0;
    }

    @Override
    public Boolean changeRole(Long id, String userRole) {
        System.out.println(id);
        System.out.println(userRole);
        User user = this.baseMapper.selectById(id);
        ArrayList<String> list = new ArrayList<>();
        list.add("teacher");list.add("student");
        if(user.getUserRole().equals(UserConstant.ADMIN_ROLE)){
            throw new CourseException(ErrorCode.PARAM_IS_WRONG,"Admin can not be changed !");
        }
        if(userRole == user.getUserRole()){
            throw new CourseException(ErrorCode.PARAM_IS_WRONG,"new role should be different");
        }
        if(userRole == UserConstant.ADMIN_ROLE){
            throw new CourseException(ErrorCode.PARAM_IS_WRONG,"can not create Admin role");
        }
        if(!list.contains(userRole)){
            throw new CourseException(ErrorCode.PARAM_IS_WRONG,"must be teacher or student ");
        }

//        if(UserConstant.ADMIN_ROLE.equals(userRole)
//                || user.getUserRole().equals(userRole)
//                || !list.contains(userRole)){
//            throw new CourseException(ErrorCode.PARAM_IS_WRONG,"Wrong Role !");
//        }
        user.setUserRole(userRole);
        this.baseMapper.updateById(user);
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = baseMapper.selectList(null);
        return users;

    }
}




