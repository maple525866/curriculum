package com.curriculum.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 班级实体类
 */
@Data
public class Class {
    /** 班级ID */
    private Integer classId;
    
    /** 班级名称 */
    private String className;
    
    /** 年级ID */
    private Integer gradeId;
    
    /** 创建时间 */
    private LocalDateTime createTime;
    
    /** 更新时间 */
    private LocalDateTime updateTime;
    
    /** 年级名称（关联查询使用） */
    private String gradeName;
}

