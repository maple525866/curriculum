-- 课程表管理系统数据库设计
-- 注意：使用前请先创建数据库 CREATE DATABASE curriculum_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 1. 年级表
CREATE TABLE IF NOT EXISTS `grade` (
    `grade_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '年级ID',
    `grade_name` VARCHAR(50) NOT NULL UNIQUE COMMENT '年级名称，如：2021级、2022级',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='年级表';

-- 2. 班级表
CREATE TABLE IF NOT EXISTS `class` (
    `class_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '班级ID',
    `class_name` VARCHAR(50) NOT NULL COMMENT '班级名称，如：计算机1班',
    `grade_id` INT NOT NULL COMMENT '年级ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (`grade_id`) REFERENCES `grade`(`grade_id`) ON DELETE CASCADE,
    UNIQUE KEY `uk_grade_class` (`grade_id`, `class_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

-- 3. 学生表
CREATE TABLE IF NOT EXISTS `student` (
    `student_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '学生ID',
    `student_name` VARCHAR(50) NOT NULL COMMENT '学生姓名',
    `student_no` VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    `class_id` INT NOT NULL COMMENT '班级ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (`class_id`) REFERENCES `class`(`class_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- 4. 课程表
CREATE TABLE IF NOT EXISTS `course` (
    `course_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '课程ID',
    `course_name` VARCHAR(100) NOT NULL COMMENT '课程名称',
    `teacher` VARCHAR(50) COMMENT '授课教师',
    `classroom` VARCHAR(50) COMMENT '教室',
    `credit` DECIMAL(3,1) COMMENT '学分',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY `uk_course_name` (`course_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- 5. 学生课程关联表（课程表安排）
CREATE TABLE IF NOT EXISTS `student_course` (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `student_id` INT NOT NULL COMMENT '学生ID',
    `course_id` INT NOT NULL COMMENT '课程ID',
    `weekday` TINYINT NOT NULL COMMENT '星期几（1-7，1表示星期一）',
    `time_slot` TINYINT NOT NULL COMMENT '第几节课（1-12）',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (`student_id`) REFERENCES `student`(`student_id`) ON DELETE CASCADE,
    FOREIGN KEY (`course_id`) REFERENCES `course`(`course_id`) ON DELETE CASCADE,
    UNIQUE KEY `uk_student_time` (`student_id`, `weekday`, `time_slot`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生课程关联表';

-- 插入测试数据
-- 年级数据
INSERT INTO `grade` (`grade_name`) VALUES 
('2021级'), ('2022级'), ('2023级');

-- 班级数据
INSERT INTO `class` (`class_name`, `grade_id`) VALUES 
('计算机1班', 1), ('计算机2班', 1), 
('软件工程1班', 2), ('软件工程2班', 2);

-- 学生数据
INSERT INTO `student` (`student_name`, `student_no`, `class_id`) VALUES 
('张三', '2021001', 1),
('李四', '2021002', 1),
('王五', '2022001', 3);

-- 课程数据
INSERT INTO `course` (`course_name`, `teacher`, `classroom`, `credit`) VALUES 
('数据库原理', '王老师', 'A101', 3.0),
('操作系统', '李老师', 'A102', 3.0),
('计算机网络', '赵老师', 'A103', 2.5),
('软件工程', '陈老师', 'B201', 3.0),
('数据结构', '刘老师', 'B202', 4.0);

-- 学生课程安排示例（张三的课程表）
INSERT INTO `student_course` (`student_id`, `course_id`, `weekday`, `time_slot`) VALUES 
(1, 1, 1, 1),  -- 周一第1节：数据库原理
(1, 1, 1, 2),  -- 周一第2节：数据库原理
(1, 2, 2, 3),  -- 周二第3节：操作系统
(1, 2, 2, 4),  -- 周二第4节：操作系统
(1, 3, 3, 1),  -- 周三第1节：计算机网络
(1, 5, 4, 5);  -- 周四第5节：数据结构

