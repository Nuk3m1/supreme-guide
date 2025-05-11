package com.example.coursemanagementsystem.service;

import com.example.coursemanagementsystem.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.coursemanagementsystem.entity.CourseAddRequest;

import java.util.List;

/**
* @author legion
* @description 针对表【course(课程)】的数据库操作Service
* @createDate 2025-03-24 10:39:58
*/
public interface CourseService extends IService<Course> {

    Long addCourse(CourseAddRequest courseAddRequest);

    Boolean disbandCourse(Long id);

    Boolean editCourse(Long id , Course course);



    List<Course> getCourse(Long id);

    List<Course> getAllCourses();
}
