package com.curriculum.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 学生实体类
 */
@Data
public class Student {
    /** 学生ID */
    private Integer studentId;
    
    /** 学生姓名 */
    private String studentName;
    
    /** 学号 */
    private String studentNo;
    
    /** 班级ID */
    private Integer classId;
    
    /** 创建时间 */
    private LocalDateTime createTime;
    
    /** 更新时间 */
    private LocalDateTime updateTime;
    
    /** 班级名称（关联查询使用） */
    private String className;
    
    /** 年级名称（关联查询使用） */
    private String gradeName;
}

