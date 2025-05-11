package com.example.coursemanagementsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * course
 * @TableName Course
 */
@TableName(value ="Course")
@Data
public class Course implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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

    /**
     * 课程所属教师id
     */
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}