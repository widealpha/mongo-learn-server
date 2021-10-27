package top.widealpha.mongodemo;

import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.widealpha.mongodemo.bean.Course;
import top.widealpha.mongodemo.bean.Student;
import top.widealpha.mongodemo.bean.Teacher;
import top.widealpha.mongodemo.dao.MongoDao;

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
    String addStudent(String sid, Integer age, String sex, String name, String dname,
                      @RequestParam("class") String className, String birthday) {
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
    String addTeacher(String tid, Integer age, String sex, String name, String dname) {
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
    String addCourse(String cid, Double credit, String fcid, String name) {
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
}
