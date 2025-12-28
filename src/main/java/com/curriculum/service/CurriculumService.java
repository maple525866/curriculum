package com.curriculum.service;

import com.curriculum.entity.Course;
import com.curriculum.entity.Student;
import com.curriculum.entity.StudentCourse;
import com.curriculum.mapper.StudentCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 课程表Service
 */
@Service
public class CurriculumService {
    
    @Autowired
    private StudentCourseMapper studentCourseMapper;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private CourseService courseService;
    
    /**
     * 为学生添加课程
     */
    @Transactional
    public boolean addCourseToStudent(Integer studentId, Integer courseId, Integer weekday, Integer timeSlot) {
        // 检查学生是否存在
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            System.out.println("学生不存在！");
            return false;
        }
        
        // 检查课程是否存在
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            System.out.println("课程不存在！");
            return false;
        }
        
        // 检查时间冲突
        StudentCourse existCourse = studentCourseMapper.selectByStudentIdAndTime(studentId, weekday, timeSlot);
        if (existCourse != null) {
            System.out.println("该时间段已有课程：" + existCourse.getCourseName());
            return false;
        }
        
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(studentId);
        studentCourse.setCourseId(courseId);
        studentCourse.setWeekday(weekday);
        studentCourse.setTimeSlot(timeSlot);
        
        return studentCourseMapper.insert(studentCourse) > 0;
    }
    
    /**
     * 删除学生的某个课程
     */
    @Transactional
    public boolean removeCourseFromStudent(Integer id) {
        return studentCourseMapper.deleteById(id) > 0;
    }
    
    /**
     * 查询学生的课程表
     */
    public List<StudentCourse> getStudentCourses(Integer studentId) {
        return studentCourseMapper.selectByStudentId(studentId);
    }
    
    /**
     * 查询学生某天的课程
     */
    public List<StudentCourse> getStudentCoursesByWeekday(Integer studentId, Integer weekday) {
        return studentCourseMapper.selectByStudentIdAndWeekday(studentId, weekday);
    }
    
    /**
     * 打印学生的课程表
     */
    public void printStudentCurriculum(Integer studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            System.out.println("学生不存在！");
            return;
        }
        
        List<StudentCourse> courses = getStudentCourses(studentId);
        
        System.out.println("\n═════════════════════════════════════════════════");
        System.out.println("学生：" + student.getStudentName() + " (" + student.getStudentNo() + ")");
        System.out.println("班级：" + student.getGradeName() + " " + student.getClassName());
        System.out.println("═════════════════════════════════════════════════");
        
        if (courses.isEmpty()) {
            System.out.println("暂无课程安排");
            return;
        }
        
        // 按星期分组
        Map<Integer, List<StudentCourse>> groupedByWeekday = courses.stream()
            .collect(Collectors.groupingBy(StudentCourse::getWeekday, TreeMap::new, Collectors.toList()));
        
        String[] weekdayNames = {"", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        
        for (Map.Entry<Integer, List<StudentCourse>> entry : groupedByWeekday.entrySet()) {
            System.out.println("\n【" + weekdayNames[entry.getKey()] + "】");
            System.out.println("─────────────────────────────────────────────────");
            
            List<StudentCourse> dayCourses = entry.getValue();
            dayCourses.sort(Comparator.comparing(StudentCourse::getTimeSlot));
            
            for (StudentCourse sc : dayCourses) {
                System.out.printf("  第%2d节 | %-20s | %-10s | %s\n",
                    sc.getTimeSlot(),
                    sc.getCourseName(),
                    sc.getTeacher(),
                    sc.getClassroom());
            }
        }
        System.out.println("═════════════════════════════════════════════════\n");
    }
}

