package com.example.coursemanagementsystem.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.coursemanagementsystem.constant.UserConstant;
import com.example.coursemanagementsystem.entity.Course;
import com.example.coursemanagementsystem.entity.CourseAddRequest;
import com.example.coursemanagementsystem.entity.User;
import com.example.coursemanagementsystem.enums.ErrorCode;
import com.example.coursemanagementsystem.exception.CourseException;
import com.example.coursemanagementsystem.service.CourseService;
import com.example.coursemanagementsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Resource
    private CourseService courseService;
    @Resource
    private UserService userService;
    /**
     * 创建课程 -create
     * @return  返回值-课程id
     * !!! 这段代码缺少权限确认机制 !!!
     */
    @PostMapping("")
    @SaCheckRole(value = {UserConstant.TEACHER_ROLE})
    public Long addCourse(@RequestBody CourseAddRequest courseAddRequest)  {

        Long courseId = courseService.addCourse(courseAddRequest);


        return courseId ;
    }

    /**
     * 删除课程 -delete
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @SaCheckRole(value = {UserConstant.TEACHER_ROLE})
    public Boolean disbandCourse(@PathVariable Long id) {
        if(id == null){
            throw new CourseException(ErrorCode.NOT_FOUND_ERROR);
        }


        Boolean result = courseService.disbandCourse(id);

        return result;
    }

    /**
     * 编辑课程 -update
     * @param course
     * @return
     */
    @PutMapping("/{id}")
    public Boolean editCourse(@PathVariable Long id ,@RequestBody Course course) {



        Boolean result = courseService.editCourse(id , course);

        return result;
    }

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public List<Course> getCourse(@PathVariable Long id) {

        List<Course> course = courseService.getCourse(id);

        return course;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return courses;
    }



}
