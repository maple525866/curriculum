package com.curriculum.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 学生课程关联实体类
 */
@Data
public class StudentCourse {
    /** 主键ID */
    private Integer id;
    
    /** 学生ID */
    private Integer studentId;
    
    /** 课程ID */
    private Integer courseId;
    
    /** 星期几（1-7，1表示星期一） */
    private Integer weekday;
    
    /** 第几节课（1-12） */
    private Integer timeSlot;
    
    /** 创建时间 */
    private LocalDateTime createTime;
    
    /** 课程名称（关联查询使用） */
    private String courseName;
    
    /** 授课教师（关联查询使用） */
    private String teacher;
    
    /** 教室（关联查询使用） */
    private String classroom;
}

