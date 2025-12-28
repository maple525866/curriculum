package com.curriculum.service;

import com.curriculum.entity.Course;
import com.curriculum.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程Service
 */
@Service
public class CourseService {
    
    @Autowired
    private CourseMapper courseMapper;
    
    /**
     * 查询所有课程
     */
    public List<Course> getAllCourses() {
        return courseMapper.selectAll();
    }
    
    /**
     * 根据ID查询课程
     */
    public Course getCourseById(Integer courseId) {
        return courseMapper.selectById(courseId);
    }
    
    /**
     * 根据课程名称查询
     */
    public Course getCourseByName(String courseName) {
        return courseMapper.selectByCourseName(courseName);
    }
    
    /**
     * 添加课程
     */
    public boolean addCourse(Course course) {
        return courseMapper.insert(course) > 0;
    }
    
    /**
     * 更新课程
     */
    public boolean updateCourse(Course course) {
        return courseMapper.update(course) > 0;
    }
    
    /**
     * 删除课程
     */
    public boolean deleteCourse(Integer courseId) {
        return courseMapper.deleteById(courseId) > 0;
    }
}

