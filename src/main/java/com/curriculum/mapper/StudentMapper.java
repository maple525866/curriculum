package com.curriculum.mapper;

import com.curriculum.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生Mapper接口
 */
@Mapper
public interface StudentMapper {
    
    /**
     * 查询所有学生
     */
    List<Student> selectAll();
    
    /**
     * 根据ID查询学生
     */
    Student selectById(@Param("studentId") Integer studentId);
    
    /**
     * 根据学号查询学生
     */
    Student selectByStudentNo(@Param("studentNo") String studentNo);
    
    /**
     * 插入学生
     */
    int insert(Student student);
    
    /**
     * 更新学生
     */
    int update(Student student);
    
    /**
     * 删除学生
     */
    int deleteById(@Param("studentId") Integer studentId);
}

