package com.example.coursemanagementsystem.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum UserRoleEnum {
    STUDENT("学生","student"),
    TEACHER("教师","teacher"),
    ADMIN("管理员","admin");





    private final String text;
    private final String value;
    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
    UserRoleEnum(String text , String role){
        this.text = text;
        this.value = role;
    }

    /**
     * 将所有角色的 role 值存入一个List
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values())
                .map(item -> item.value)
                .collect(Collectors.toList());
    }


}
