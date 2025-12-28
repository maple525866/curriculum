package com.curriculum.service;

import com.curriculum.entity.Student;
import com.curriculum.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生Service
 */
@Service
public class StudentService {
    
    @Autowired
    private StudentMapper studentMapper;
    
    /**
     * 查询所有学生
     */
    public List<Student> getAllStudents() {
        return studentMapper.selectAll();
    }
    
    /**
     * 根据ID查询学生
     */
    public Student getStudentById(Integer studentId) {
        return studentMapper.selectById(studentId);
    }
    
    /**
     * 根据学号查询学生
     */
    public Student getStudentByNo(String studentNo) {
        return studentMapper.selectByStudentNo(studentNo);
    }
    
    /**
     * 添加学生
     */
    public boolean addStudent(Student student) {
        return studentMapper.insert(student) > 0;
    }
    
    /**
     * 更新学生
     */
    public boolean updateStudent(Student student) {
        return studentMapper.update(student) > 0;
    }
    
    /**
     * 删除学生
     */
    public boolean deleteStudent(Integer studentId) {
        return studentMapper.deleteById(studentId) > 0;
    }
}

