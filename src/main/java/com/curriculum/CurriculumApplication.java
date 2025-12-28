package com.curriculum;

import com.curriculum.entity.Course;
import com.curriculum.entity.Student;
import com.curriculum.entity.StudentCourse;
import com.curriculum.service.CourseService;
import com.curriculum.service.CurriculumService;
import com.curriculum.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * 课程表管理系统主程序
 */
@SpringBootApplication
public class CurriculumApplication implements CommandLineRunner {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CurriculumService curriculumService;

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SpringApplication.run(CurriculumApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║      欢迎使用课程表管理系统               ║");
        System.out.println("╚═══════════════════════════════════════╝\n");

        while (true) {
            showMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    studentManagement();
                    break;
                case "2":
                    courseManagement();
                    break;
                case "3":
                    curriculumManagement();
                    break;
                case "0":
                    System.out.println("\n感谢使用，再见！");
                    System.exit(0);
                    return;
                default:
                    System.out.println("\n❌ 无效的选择，请重新输入！");
            }
        }
    }

    /**
     * 显示主菜单
     */
    private void showMainMenu() {
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│            主菜单                    │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  1. 学生管理                         │");
        System.out.println("│  2. 课程管理                         │");
        System.out.println("│  3. 课程表管理                       │");
        System.out.println("│  0. 退出系统                         │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("请选择：");
    }

    /**
     * 学生管理
     */
    private void studentManagement() {
        while (true) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│          学生管理                    │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. 查看所有学生                     │");
            System.out.println("│  2. 添加学生                         │");
            System.out.println("│  3. 更新学生信息                     │");
            System.out.println("│  4. 删除学生                         │");
            System.out.println("│  0. 返回主菜单                       │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("请选择：");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    viewAllStudents();
                    break;
                case "2":
                    addStudent();
                    break;
                case "3":
                    updateStudent();
                    break;
                case "4":
                    deleteStudent();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("\n❌ 无效的选择！");
            }
        }
    }

    /**
     * 查看所有学生
     */
    private void viewAllStudents() {
        List<Student> students = studentService.getAllStudents();
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("                        学生列表");
        System.out.println("═══════════════════════════════════════════════════════════");
        if (students.isEmpty()) {
            System.out.println("暂无学生数据");
        } else {
            System.out.printf("%-5s %-10s %-15s %-15s %-10s\n", 
                "ID", "姓名", "学号", "年级", "班级");
            System.out.println("───────────────────────────────────────────────────────────");
            for (Student student : students) {
                System.out.printf("%-5d %-10s %-15s %-15s %-10s\n",
                    student.getStudentId(),
                    student.getStudentName(),
                    student.getStudentNo(),
                    student.getGradeName(),
                    student.getClassName());
            }
        }
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }

    /**
     * 添加学生
     */
    private void addStudent() {
        System.out.println("\n--- 添加学生 ---");
        System.out.print("请输入学生姓名：");
        String name = scanner.nextLine().trim();
        
        System.out.print("请输入学号：");
        String studentNo = scanner.nextLine().trim();
        
        System.out.print("请输入班级ID：");
        String classIdStr = scanner.nextLine().trim();
        
        try {
            Integer classId = Integer.parseInt(classIdStr);
            Student student = new Student();
            student.setStudentName(name);
            student.setStudentNo(studentNo);
            student.setClassId(classId);
            
            if (studentService.addStudent(student)) {
                System.out.println("\n✓ 学生添加成功！");
            } else {
                System.out.println("\n❌ 学生添加失败！");
            }
        } catch (Exception e) {
            System.out.println("\n❌ 输入有误：" + e.getMessage());
        }
    }

    /**
     * 更新学生信息
     */
    private void updateStudent() {
        System.out.print("\n请输入要更新的学生ID：");
        String idStr = scanner.nextLine().trim();
        
        try {
            Integer studentId = Integer.parseInt(idStr);
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                System.out.println("\n❌ 学生不存在！");
                return;
            }
            
            System.out.println("\n当前学生信息：");
            System.out.println("姓名：" + student.getStudentName());
            System.out.println("学号：" + student.getStudentNo());
            System.out.println("班级ID：" + student.getClassId() + " (" + student.getGradeName() + " " + student.getClassName() + ")");
            
            System.out.println("\n--- 请输入新的信息（直接回车保持不变） ---");
            
            System.out.print("新的学生姓名：");
            String newName = scanner.nextLine().trim();
            if (!newName.isEmpty()) {
                student.setStudentName(newName);
            }
            
            System.out.print("新的学号：");
            String newStudentNo = scanner.nextLine().trim();
            if (!newStudentNo.isEmpty()) {
                student.setStudentNo(newStudentNo);
            }
            
            System.out.print("新的班级ID：");
            String newClassIdStr = scanner.nextLine().trim();
            if (!newClassIdStr.isEmpty()) {
                Integer newClassId = Integer.parseInt(newClassIdStr);
                student.setClassId(newClassId);
            }
            
            if (studentService.updateStudent(student)) {
                System.out.println("\n✓ 学生信息更新成功！");
            } else {
                System.out.println("\n❌ 学生信息更新失败！");
            }
        } catch (Exception e) {
            System.out.println("\n❌ 输入有误：" + e.getMessage());
        }
    }

    /**
     * 删除学生
     */
    private void deleteStudent() {
        System.out.print("\n请输入要删除的学生ID：");
        String idStr = scanner.nextLine().trim();
        
        try {
            Integer studentId = Integer.parseInt(idStr);
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                System.out.println("\n❌ 学生不存在！");
                return;
            }
            
            System.out.print("确认删除学生 [" + student.getStudentName() + "] 吗？(y/n)：");
            String confirm = scanner.nextLine().trim();
            
            if ("y".equalsIgnoreCase(confirm)) {
                if (studentService.deleteStudent(studentId)) {
                    System.out.println("\n✓ 学生删除成功！");
                } else {
                    System.out.println("\n❌ 学生删除失败！");
                }
            }
        } catch (Exception e) {
            System.out.println("\n❌ 输入有误：" + e.getMessage());
        }
    }

    /**
     * 课程管理
     */
    private void courseManagement() {
        while (true) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│          课程管理                    │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. 查看所有课程                     │");
            System.out.println("│  2. 添加课程                         │");
            System.out.println("│  3. 更新课程信息                     │");
            System.out.println("│  4. 删除课程                         │");
            System.out.println("│  0. 返回主菜单                       │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("请选择：");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    viewAllCourses();
                    break;
                case "2":
                    addCourse();
                    break;
                case "3":
                    updateCourse();
                    break;
                case "4":
                    deleteCourse();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("\n❌ 无效的选择！");
            }
        }
    }

    /**
     * 查看所有课程
     */
    private void viewAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("                        课程列表");
        System.out.println("═══════════════════════════════════════════════════════════");
        if (courses.isEmpty()) {
            System.out.println("暂无课程数据");
        } else {
            System.out.printf("%-5s %-20s %-10s %-10s %-6s\n", 
                "ID", "课程名称", "教师", "教室", "学分");
            System.out.println("───────────────────────────────────────────────────────────");
            for (Course course : courses) {
                System.out.printf("%-5d %-20s %-10s %-10s %-6s\n",
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getTeacher(),
                    course.getClassroom(),
                    course.getCredit());
            }
        }
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }

    /**
     * 添加课程
     */
    private void addCourse() {
        System.out.println("\n--- 添加课程 ---");
        System.out.print("请输入课程名称：");
        String courseName = scanner.nextLine().trim();
        
        System.out.print("请输入教师姓名：");
        String teacher = scanner.nextLine().trim();
        
        System.out.print("请输入教室：");
        String classroom = scanner.nextLine().trim();
        
        System.out.print("请输入学分：");
        String creditStr = scanner.nextLine().trim();
        
        try {
            BigDecimal credit = new BigDecimal(creditStr);
            Course course = new Course();
            course.setCourseName(courseName);
            course.setTeacher(teacher);
            course.setClassroom(classroom);
            course.setCredit(credit);
            
            if (courseService.addCourse(course)) {
                System.out.println("\n✓ 课程添加成功！");
            } else {
                System.out.println("\n❌ 课程添加失败！");
            }
        } catch (Exception e) {
            System.out.println("\n❌ 输入有误：" + e.getMessage());
        }
    }

    /**
     * 更新课程信息
     */
    private void updateCourse() {
        System.out.print("\n请输入要更新的课程ID：");
        String idStr = scanner.nextLine().trim();
        
        try {
            Integer courseId = Integer.parseInt(idStr);
            Course course = courseService.getCourseById(courseId);
            if (course == null) {
                System.out.println("\n❌ 课程不存在！");
                return;
            }
            
            System.out.println("\n当前课程信息：");
            System.out.println("课程名称：" + course.getCourseName());
            System.out.println("授课教师：" + course.getTeacher());
            System.out.println("教室：" + course.getClassroom());
            System.out.println("学分：" + course.getCredit());
            
            System.out.println("\n--- 请输入新的信息（直接回车保持不变） ---");
            
            System.out.print("新的课程名称：");
            String newCourseName = scanner.nextLine().trim();
            if (!newCourseName.isEmpty()) {
                course.setCourseName(newCourseName);
            }
            
            System.out.print("新的授课教师：");
            String newTeacher = scanner.nextLine().trim();
            if (!newTeacher.isEmpty()) {
                course.setTeacher(newTeacher);
            }
            
            System.out.print("新的教室：");
            String newClassroom = scanner.nextLine().trim();
            if (!newClassroom.isEmpty()) {
                course.setClassroom(newClassroom);
            }
            
            System.out.print("新的学分：");
            String newCreditStr = scanner.nextLine().trim();
            if (!newCreditStr.isEmpty()) {
                BigDecimal newCredit = new BigDecimal(newCreditStr);
                course.setCredit(newCredit);
            }
            
            if (courseService.updateCourse(course)) {
                System.out.println("\n✓ 课程信息更新成功！");
            } else {
                System.out.println("\n❌ 课程信息更新失败！");
            }
        } catch (Exception e) {
            System.out.println("\n❌ 输入有误：" + e.getMessage());
        }
    }

    /**
     * 删除课程
     */
    private void deleteCourse() {
        System.out.print("\n请输入要删除的课程ID：");
        String idStr = scanner.nextLine().trim();
        
        try {
            Integer courseId = Integer.parseInt(idStr);
            Course course = courseService.getCourseById(courseId);
            if (course == null) {
                System.out.println("\n❌ 课程不存在！");
                return;
            }
            
            System.out.print("确认删除课程 [" + course.getCourseName() + "] 吗？(y/n)：");
            String confirm = scanner.nextLine().trim();
            
            if ("y".equalsIgnoreCase(confirm)) {
                if (courseService.deleteCourse(courseId)) {
                    System.out.println("\n✓ 课程删除成功！");
                } else {
                    System.out.println("\n❌ 课程删除失败！");
                }
            }
        } catch (Exception e) {
            System.out.println("\n❌ 输入有误：" + e.getMessage());
        }
    }

    /**
     * 课程表管理
     */
    private void curriculumManagement() {
        while (true) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│        课程表管理                    │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. 查看学生课程表                   │");
            System.out.println("│  2. 为学生添加课程                   │");
            System.out.println("│  3. 修改课程时间                     │");
            System.out.println("│  4. 删除学生的课程                   │");
            System.out.println("│  0. 返回主菜单                       │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("请选择：");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    viewStudentCurriculum();
                    break;
                case "2":
                    addCourseToStudent();
                    break;
                case "3":
                    updateCourseTime();
                    break;
                case "4":
                    removeCourseFromStudent();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("\n❌ 无效的选择！");
            }
        }
    }

    /**
     * 查看学生课程表
     */
    private void viewStudentCurriculum() {
        System.out.print("\n请输入学生ID：");
        String idStr = scanner.nextLine().trim();
        
        try {
            Integer studentId = Integer.parseInt(idStr);
            curriculumService.printStudentCurriculum(studentId);
        } catch (Exception e) {
            System.out.println("\n❌ 输入有误：" + e.getMessage());
        }
    }

    /**
     * 为学生添加课程
     */
    private void addCourseToStudent() {
        System.out.println("\n--- 为学生添加课程 ---");
        System.out.print("请输入学生ID：");
        String studentIdStr = scanner.nextLine().trim();
        
        System.out.print("请输入课程ID：");
        String courseIdStr = scanner.nextLine().trim();
        
        System.out.print("请输入星期几 (1-7，1表示星期一)：");
        String weekdayStr = scanner.nextLine().trim();
        
        System.out.print("请输入第几节课 (1-12)：");
        String timeSlotStr = scanner.nextLine().trim();
        
        try {
            Integer studentId = Integer.parseInt(studentIdStr);
            Integer courseId = Integer.parseInt(courseIdStr);
            Integer weekday = Integer.parseInt(weekdayStr);
            Integer timeSlot = Integer.parseInt(timeSlotStr);
            
            if (weekday < 1 || weekday > 7) {
                System.out.println("\n❌ 星期数必须在1-7之间！");
                return;
            }
            
            if (timeSlot < 1 || timeSlot > 12) {
                System.out.println("\n❌ 节次必须在1-12之间！");
                return;
            }
            
            if (curriculumService.addCourseToStudent(studentId, courseId, weekday, timeSlot)) {
                System.out.println("\n✓ 课程添加成功！");
            } else {
                System.out.println("\n❌ 课程添加失败！");
            }
        } catch (Exception e) {
            System.out.println("\n❌ 输入有误：" + e.getMessage());
        }
    }

    /**
     * 修改课程时间
     */
    private void updateCourseTime() {
        System.out.print("\n请输入学生ID：");
        String studentIdStr = scanner.nextLine().trim();
        
        try {
            Integer studentId = Integer.parseInt(studentIdStr);
            List<StudentCourse> courses = curriculumService.getStudentCourses(studentId);
            
            if (courses.isEmpty()) {
                System.out.println("\n该学生暂无课程！");
                return;
            }
            
            System.out.println("\n学生的课程列表：");
            System.out.println("─────────────────────────────────────────────────");
            for (StudentCourse sc : courses) {
                String[] weekdays = {"", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};
                System.out.printf("ID: %-3d | %s 第%2d节 | %s\n",
                    sc.getId(),
                    weekdays[sc.getWeekday()],
                    sc.getTimeSlot(),
                    sc.getCourseName());
            }
            System.out.println("─────────────────────────────────────────────────");
            
            System.out.print("\n请输入要修改的课程记录ID：");
            String idStr = scanner.nextLine().trim();
            Integer id = Integer.parseInt(idStr);
            
            System.out.print("请输入新的星期几 (1-7)：");
            String newWeekdayStr = scanner.nextLine().trim();
            Integer newWeekday = Integer.parseInt(newWeekdayStr);
            
            System.out.print("请输入新的第几节课 (1-12)：");
            String newTimeSlotStr = scanner.nextLine().trim();
            Integer newTimeSlot = Integer.parseInt(newTimeSlotStr);
            
            if (newWeekday < 1 || newWeekday > 7) {
                System.out.println("\n❌ 星期数必须在1-7之间！");
                return;
            }
            
            if (newTimeSlot < 1 || newTimeSlot > 12) {
                System.out.println("\n❌ 节次必须在1-12之间！");
                return;
            }
            
            if (curriculumService.updateCourseTime(id, newWeekday, newTimeSlot)) {
                System.out.println("\n✓ 课程时间修改成功！");
            } else {
                System.out.println("\n❌ 课程时间修改失败！");
            }
        } catch (Exception e) {
            System.out.println("\n❌ 输入有误：" + e.getMessage());
        }
    }

    /**
     * 删除学生的课程
     */
    private void removeCourseFromStudent() {
        System.out.print("\n请输入学生ID：");
        String studentIdStr = scanner.nextLine().trim();
        
        try {
            Integer studentId = Integer.parseInt(studentIdStr);
            List<StudentCourse> courses = curriculumService.getStudentCourses(studentId);
            
            if (courses.isEmpty()) {
                System.out.println("\n该学生暂无课程！");
                return;
            }
            
            System.out.println("\n学生的课程列表：");
            System.out.println("─────────────────────────────────────────────────");
            for (StudentCourse sc : courses) {
                String[] weekdays = {"", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};
                System.out.printf("ID: %-3d | %s 第%2d节 | %s\n",
                    sc.getId(),
                    weekdays[sc.getWeekday()],
                    sc.getTimeSlot(),
                    sc.getCourseName());
            }
            System.out.println("─────────────────────────────────────────────────");
            
            System.out.print("\n请输入要删除的课程记录ID：");
            String idStr = scanner.nextLine().trim();
            Integer id = Integer.parseInt(idStr);
            
            if (curriculumService.removeCourseFromStudent(id)) {
                System.out.println("\n✓ 课程删除成功！");
            } else {
                System.out.println("\n❌ 课程删除失败！");
            }
        } catch (Exception e) {
            System.out.println("\n❌ 输入有误：" + e.getMessage());
        }
    }
}

