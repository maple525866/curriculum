package com.curriculum.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程实体类
 */
@Data
public class Course {
    /** 课程ID */
    private Integer courseId;
    
    /** 课程名称 */
    private String courseName;
    
    /** 授课教师 */
    private String teacher;
    
    /** 教室 */
    private String classroom;
    
    /** 学分 */
    private BigDecimal credit;
    
    /** 创建时间 */
    private LocalDateTime createTime;
    
    /** 更新时间 */
    private LocalDateTime updateTime;
}

