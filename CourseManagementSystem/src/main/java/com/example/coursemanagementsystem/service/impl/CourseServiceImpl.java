package com.example.coursemanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.coursemanagementsystem.entity.Course;
import com.example.coursemanagementsystem.entity.CourseAddRequest;
import com.example.coursemanagementsystem.enums.ErrorCode;
import com.example.coursemanagementsystem.exception.CourseException;
import com.example.coursemanagementsystem.service.CourseService;
import com.example.coursemanagementsystem.mapper.CourseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
* @author legion
* @description 针对表【course(课程)】的数据库操作Service实现
* @createDate 2025-03-24 10:39:58
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{

    @Override
    public Long addCourse(CourseAddRequest courseAddRequest){
        if(courseAddRequest.getHours() <= 0 ){
            throw new CourseException(ErrorCode.PARAM_IS_WRONG,"param is wrong");
        }
        Course course = new Course();
        BeanUtils.copyProperties(courseAddRequest, course);

        boolean result = baseMapper.insert(course) != 0;

        System.out.println(result);
        return course.getId();
    }
    @Override
    public Boolean disbandCourse(Long id){
        boolean exists = baseMapper.exists(Wrappers.<Course>lambdaQuery()
                .eq(Course::getId, id));
        if(!exists){
            throw new CourseException(ErrorCode.NOT_FOUND_ERROR,"Course is not exists");
        }
        baseMapper.deleteById(id);
        return true;
    }
    @Override
    public Boolean editCourse(Long id ,Course course){


        //当学时hours为负数时，抛出参数异常
        if(course.getHours() <= 0 ){
            throw new CourseException(ErrorCode.PARAM_IS_WRONG,"param is wrong");
        }
        Course editCourse = baseMapper.selectById(id);
        validCourse(editCourse,course);
        baseMapper.updateById(editCourse);
        return true;
    }

    @Override
    public List<Course> getCourse(Long id) {


        List<Course> course = new ArrayList<>();
        if (id == null) {
            List<Course> courses = baseMapper.selectList(null);
            course.addAll(courses);
            ;
        } else {
            Course singlecourse = baseMapper.selectById(id);
            course.add(singlecourse);
        }
        return course;}
/*        if (id == null) {
            return baseMapper.selectList(null);
        } else {
            Course course = baseMapper.selectById(id);
            List<Course> courseList = Collections.singletonList(course);
            return courseList;
        }
    }*/
    @Override
    public List<Course> getAllCourses(){
        List<Course> courses = baseMapper.selectList(null);
        return courses;
    }

    private void validCourse(Course old ,Course course){
        old.setName(course.getName());
        old.setHours(course.getHours());
        old.setDescription(course.getDescription());
        old.setFilePath(course.getFilePath());
        old.setStatus(course.getStatus());
        old.setTeacherId(course.getTeacherId());
        old.setStartDate(course.getStartDate());
        old.setEndDate(course.getEndDate());
        old.setUpdateTime(course.getUpdateTime());

    }


}




