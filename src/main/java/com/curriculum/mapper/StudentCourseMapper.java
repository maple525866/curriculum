package com.curriculum.mapper;

import com.curriculum.entity.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生课程Mapper接口
 */
@Mapper
public interface StudentCourseMapper {
    
    /**
     * 查询学生的所有课程
     */
    List<StudentCourse> selectByStudentId(@Param("studentId") Integer studentId);
    
    /**
     * 查询学生某天的课程
     */
    List<StudentCourse> selectByStudentIdAndWeekday(
        @Param("studentId") Integer studentId,
        @Param("weekday") Integer weekday
    );
    
    /**
     * 检查时间冲突
     */
    StudentCourse selectByStudentIdAndTime(
        @Param("studentId") Integer studentId,
        @Param("weekday") Integer weekday,
        @Param("timeSlot") Integer timeSlot
    );
    
    /**
     * 插入学生课程
     */
    int insert(StudentCourse studentCourse);
    
    /**
     * 删除学生课程
     */
    int deleteById(@Param("id") Integer id);
    
    /**
     * 删除学生的所有课程
     */
    int deleteByStudentId(@Param("studentId") Integer studentId);
}

