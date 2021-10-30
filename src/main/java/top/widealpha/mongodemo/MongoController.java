package top.widealpha.mongodemo;

import com.alibaba.excel.EasyExcel;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.widealpha.mongodemo.bean.Course;
import top.widealpha.mongodemo.bean.Student;
import top.widealpha.mongodemo.bean.Teacher;
import top.widealpha.mongodemo.dao.MongoDao;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/")
public class MongoController {
    @Autowired
    MongoDao mongoDao;

    @RequestMapping("allStudents")
    List<Student> allStudent() {
        return mongoDao.allStudents();
    }

    @RequestMapping("studentsYounger20")
    List<Student> studentsYounger20() {
        return mongoDao.findAgeLessStudents(20);
    }

    @RequestMapping("findCourseWithFcid")
    List<Course> findCourseWithFcid() {
        return mongoDao.findCourseWithFcid("300001");
    }

    @RequestMapping("findCourseWithDname")
    List<Teacher> findCourseWithDname() {
        return mongoDao.findTeacherWithDname("CS");
    }

    @RequestMapping("addStudent")
    String addStudent(@RequestParam String sid,
                      @RequestParam Integer age,
                      @RequestParam String sex,
                      @RequestParam String name,
                      @RequestParam String dname,
                      @RequestParam("class") String className,
                      @RequestParam String birthday) {
        Student student = new Student();
        student.setSid(sid);
        student.setAge(age);
        student.setSex(sex);
        student.setName(name);
        student.setDname(dname);
        student.setClassName(className);
        student.setBirthday(birthday);
        try {
            mongoDao.insertStudents(Collections.singletonList(student));
            return "插入成功";
        } catch (MongoException e) {
            e.printStackTrace();
            return "插入失败";
        }
    }

    @RequestMapping("addTeacher")
    String addTeacher(@RequestParam String tid,
                      @RequestParam Integer age,
                      @RequestParam String sex,
                      @RequestParam String name,
                      @RequestParam String dname) {
        Teacher teacher = new Teacher();
        teacher.setTid(tid);
        teacher.setAge(age);
        teacher.setSex(sex);
        teacher.setName(name);
        teacher.setDname(dname);
        try {
            mongoDao.insertTeachers(Collections.singletonList(teacher));
            return "插入成功";
        } catch (MongoException e) {
            e.printStackTrace();
            return "插入失败";
        }
    }

    @RequestMapping("addCourse")
    String addCourse(@RequestParam String cid,
                     @RequestParam Double credit,
                     @RequestParam(required = false) String fcid,
                     @RequestParam String name) {
        Course course = new Course();
        course.setCid(cid);
        course.setCredit(credit);
        course.setFcid(fcid);
        course.setName(name);
        try {
            mongoDao.insertCourses(Collections.singletonList(course));
            return "插入成功";
        } catch (MongoException e) {
            e.printStackTrace();
            return "插入失败";
        }
    }

    @RequestMapping("uploadExcel")
    String uploadExcel(@RequestParam MultipartFile excel, @RequestParam String sheetName) throws IOException {
        if (excel == null || excel.isEmpty()) {
            return "插入失败";
        } else {
            switch (sheetName) {
                case "student":
                    List<Student> students = EasyExcel.read(excel.getInputStream(), Student.class, null).sheet(sheetName).doReadSync();
                    mongoDao.insertStudents(students);
                    break;
                case "teacher":
                    List<Teacher> teachers = EasyExcel.read(excel.getInputStream(), Teacher.class, null).sheet(sheetName).doReadSync();
                    mongoDao.insertTeachers(teachers);
                    break;
                case "course":
                    List<Course> courses = EasyExcel.read(excel.getInputStream(), Course.class, null).sheet(sheetName).doReadSync();
                    mongoDao.insertCourses(courses);
                    break;
                default:
                    return "插入失败";
            }
        }
        return "插入成功";
    }


    @RequestMapping("updateStudent")
    String updateStudent(@RequestParam String sid,
                         @RequestParam Integer age,
                         @RequestParam String sex,
                         @RequestParam String name,
                         @RequestParam String dname,
                         @RequestParam("class") String className,
                         @RequestParam String birthday) {
        Student student = new Student();
        student.setSid(sid);
        student.setAge(age);
        student.setSex(sex);
        student.setName(name);
        student.setDname(dname);
        student.setClassName(className);
        student.setBirthday(birthday);
        try {
            mongoDao.updateStudent(Collections.singletonList(student));
            return "更新成功";
        } catch (MongoException e) {
            e.printStackTrace();
            return "更新失败";
        }
    }

    @RequestMapping("updateTeacher")
    String updateTeacher(@RequestParam String tid,
                         @RequestParam Integer age,
                         @RequestParam String sex,
                         @RequestParam String name,
                         @RequestParam String dname) {
        Teacher teacher = new Teacher();
        teacher.setTid(tid);
        teacher.setAge(age);
        teacher.setSex(sex);
        teacher.setName(name);
        teacher.setDname(dname);
        try {
            mongoDao.updateTeacher(Collections.singletonList(teacher));
            return "更新成功";
        } catch (MongoException e) {
            e.printStackTrace();
            return "更新失败";
        }
    }

    @RequestMapping("updateCourse")
    String updateCourse(@RequestParam String cid,
                        @RequestParam Double credit,
                        @RequestParam(required = false) String fcid,
                        @RequestParam String name) {
        Course course = new Course();
        course.setCid(cid);
        course.setCredit(credit);
        course.setFcid(fcid);
        course.setName(name);
        try {
            mongoDao.updateCourse(Collections.singletonList(course));
            return "更新成功";
        } catch (MongoException e) {
            e.printStackTrace();
            return "更新失败";
        }
    }
}
