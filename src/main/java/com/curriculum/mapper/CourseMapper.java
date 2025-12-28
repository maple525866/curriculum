package com.curriculum.mapper;

import com.curriculum.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程Mapper接口
 */
@Mapper
public interface CourseMapper {
    
    /**
     * 查询所有课程
     */
    List<Course> selectAll();
    
    /**
     * 根据ID查询课程
     */
    Course selectById(@Param("courseId") Integer courseId);
    
    /**
     * 根据课程名称查询
     */
    Course selectByCourseName(@Param("courseName") String courseName);
    
    /**
     * 插入课程
     */
    int insert(Course course);
    
    /**
     * 更新课程
     */
    int update(Course course);
    
    /**
     * 删除课程
     */
    int deleteById(@Param("courseId") Integer courseId);
}

