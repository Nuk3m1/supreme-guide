package com.example.coursemanagementsystem.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CourseAddRequest implements Serializable {
    /**
     * 课程名字
     */
    private String name;

    /**
     * 学时
     */
    private Integer hours;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 该课程是否开启
     */
    private Integer status;


    private Long teacherId;

    /**
     * 开启时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 更新时间
     */
    private Date updateTime;

}
