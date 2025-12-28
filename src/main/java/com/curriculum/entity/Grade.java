package com.curriculum.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 年级实体类
 */
@Data
public class Grade {
    /** 年级ID */
    private Integer gradeId;
    
    /** 年级名称 */
    private String gradeName;
    
    /** 创建时间 */
    private LocalDateTime createTime;
    
    /** 更新时间 */
    private LocalDateTime updateTime;
}

