# 课程表管理系统

## 项目简介

这是一个基于Spring Boot + MyBatis的课程表管理系统，用于管理学生的课程安排。系统提供命令行交互界面，支持学生管理、课程管理和课程表管理等功能。

## 技术栈

- **JDK**: 17
- **Spring Boot**: 3.1.5
- **MyBatis**: 3.0.2
- **Druid**: 1.2.20（数据库连接池）
- **MySQL**: 8.0+
- **Lombok**: 简化代码

## 项目结构

```
curriculum/
├── src/
│   ├── main/
│   │   ├── java/com/curriculum/
│   │   │   ├── CurriculumApplication.java    # 主程序（命令行交互）
│   │   │   ├── entity/                        # 实体类
│   │   │   │   ├── Grade.java                 # 年级
│   │   │   │   ├── Class.java                 # 班级
│   │   │   │   ├── Student.java               # 学生
│   │   │   │   ├── Course.java                # 课程
│   │   │   │   └── StudentCourse.java         # 学生课程关联
│   │   │   ├── mapper/                        # MyBatis Mapper接口
│   │   │   │   ├── StudentMapper.java
│   │   │   │   ├── CourseMapper.java
│   │   │   │   └── StudentCourseMapper.java
│   │   │   └── service/                       # 业务逻辑层
│   │   │       ├── StudentService.java
│   │   │       ├── CourseService.java
│   │   │       └── CurriculumService.java
│   │   └── resources/
│   │       ├── application.yml                # 配置文件
│   │       ├── schema.sql                     # 数据库建表脚本
│   │       └── mapper/                        # MyBatis XML映射文件
│   │           ├── StudentMapper.xml
│   │           ├── CourseMapper.xml
│   │           └── StudentCourseMapper.xml
│   └── test/
├── pom.xml                                    # Maven配置文件
└── README.md
```

## 数据库设计

### 表结构

1. **年级表 (grade)**
   - grade_id: 年级ID（主键）
   - grade_name: 年级名称

2. **班级表 (class)**
   - class_id: 班级ID（主键）
   - class_name: 班级名称
   - grade_id: 年级ID（外键）

3. **学生表 (student)**
   - student_id: 学生ID（主键）
   - student_name: 学生姓名
   - student_no: 学号（唯一）
   - class_id: 班级ID（外键）

4. **课程表 (course)**
   - course_id: 课程ID（主键）
   - course_name: 课程名称
   - teacher: 授课教师
   - classroom: 教室
   - credit: 学分

5. **学生课程关联表 (student_course)**
   - id: 主键ID
   - student_id: 学生ID（外键）
   - course_id: 课程ID（外键）
   - weekday: 星期几（1-7）
   - time_slot: 第几节课（1-12）

## 安装与使用

### 1. 环境准备

- 安装 JDK 17
- 安装 MySQL 8.0+
- 安装 Maven 3.6+

### 2. 创建数据库

```sql
CREATE DATABASE curriculum_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 导入数据库表

执行 `src/main/resources/schema.sql` 文件中的SQL语句。

可以使用以下命令：
```bash
mysql -u root -p curriculum_db < src/main/resources/schema.sql
```

### 4. 修改数据库配置

修改 `src/main/resources/application.yml` 文件中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/curriculum_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root      # 修改为你的数据库用户名
    password: root      # 修改为你的数据库密码
```

### 5. 编译运行

```bash
# 编译项目
mvn clean package

# 运行项目
mvn spring-boot:run
```

或者使用IDE（如IntelliJ IDEA）直接运行 `CurriculumApplication.java`。

## 功能说明

### 主菜单

系统启动后会显示主菜单，包含以下选项：

1. **学生管理**
   - 查看所有学生
   - 添加学生
   - 更新学生信息
   - 删除学生

2. **课程管理**
   - 查看所有课程
   - 添加课程
   - 更新课程信息
   - 删除课程

3. **课程表管理**
   - 查看学生课程表
   - 为学生添加课程
   - 修改课程时间
   - 删除学生的课程

### 使用示例

#### 1. 查看学生课程表
- 选择菜单：3 → 1
- 输入学生ID（例如：1）
- 系统会以表格形式显示该学生的课程表

#### 2. 为学生添加课程
- 选择菜单：3 → 2
- 输入学生ID（例如：1）
- 输入课程ID（例如：4）
- 输入星期几（例如：5，表示星期五）
- 输入第几节课（例如：1）
- 系统会检查时间冲突并添加课程

#### 3. 修改学生信息
- 选择菜单：1 → 3
- 输入学生ID
- 系统显示当前学生信息
- 输入新的信息（直接回车保持不变）
- 更新成功

#### 4. 修改课程时间
- 选择菜单：3 → 3
- 输入学生ID
- 系统显示该学生的所有课程
- 输入要修改的课程记录ID
- 输入新的星期和节次
- 系统会检查时间冲突并更新

#### 5. 删除学生的课程
- 选择菜单：3 → 4
- 输入学生ID
- 系统显示该学生的所有课程
- 输入要删除的课程记录ID
- 确认删除

## 测试数据

系统已预置了一些测试数据：

- **年级**: 2021级、2022级、2023级
- **班级**: 计算机1班、计算机2班、软件工程1班、软件工程2班
- **学生**: 张三、李四、王五
- **课程**: 数据库原理、操作系统、计算机网络、软件工程、数据结构
- 张三已有部分课程安排

## 注意事项

1. 数据库连接信息需要根据实际情况修改
2. 系统使用了级联删除，删除学生或课程时会自动删除相关的课程安排
3. 添加课程时会自动检查时间冲突
4. 星期数范围：1-7（1表示星期一）
5. 节次范围：1-12

## 作者

课程表管理系统 - 数据库设计软件作业

## 许可

本项目仅供学习使用。

