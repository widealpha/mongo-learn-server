package top.widealpha.mongodemo;

import com.alibaba.excel.EasyExcel;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.widealpha.mongodemo.bean.*;
import top.widealpha.mongodemo.dao.MongoDao;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin
public class MongoController {
    @Autowired
    MongoDao mongoDao;

    @RequestMapping("allStudents")
    List<Student> allStudents() {
        List<Student> list = mongoDao.allStudents();
        return list.size() > 50 ? list.subList(0, 50) : list;
    }

    @RequestMapping("allTeachers")
    List<Teacher> allTeachers() {
        List<Teacher> list = mongoDao.allTeachers();
        return list.size() > 50 ? list.subList(0, 50) : list;
    }


    @RequestMapping("allCourses")
    List<Course> allCourses() {
        List<Course> list = mongoDao.allCourses();
        return list.size() > 50 ? list.subList(0, 50) : list;
    }

    @RequestMapping("studentsYounger20")
    List<Student> studentsYounger20() {
        return mongoDao.findAgeLessStudents(20);
    }

    @RequestMapping("findCourseWithFcid")
    List<Course> findCourseWithFcid() {
        return mongoDao.findCourseWithFcid("300001");
    }

    @RequestMapping("findTeacherWithDname")
    List<Teacher> findTeacherWithDname() {
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
            return "????????????";
        } catch (MongoException e) {
            e.printStackTrace();
            return "????????????";
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
            return "????????????";
        } catch (MongoException e) {
            e.printStackTrace();
            return "????????????";
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
            return "????????????";
        } catch (MongoException e) {
            e.printStackTrace();
            return "????????????";
        }
    }

    @RequestMapping("uploadExcel")
    String uploadExcel(@RequestParam MultipartFile excel, @RequestParam String sheetName) throws IOException {
        if (excel == null || excel.isEmpty()) {
            return "????????????";
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
                    return "????????????";
            }
        }
        return "????????????";
    }

    @RequestMapping("uploadStudentExcel")
    String uploadStudentExcel(@RequestParam MultipartFile excel) throws IOException {
        if (excel == null || excel.isEmpty()) {
            return "????????????";
        } else {
            List<Student> students = EasyExcel.read(excel.getInputStream(), Student.class, null).sheet(0).doReadSync();
            mongoDao.insertStudents(students);
        }
        return "????????????";
    }

    @RequestMapping("uploadTeacherExcel")
    String uploadTeacherExcel(@RequestParam MultipartFile excel) throws IOException {
        if (excel == null || excel.isEmpty()) {
            return "????????????";
        } else {
            List<Teacher> teachers = EasyExcel.read(excel.getInputStream(), Teacher.class, null).sheet(0).doReadSync();
            mongoDao.insertTeachers(teachers);
        }
        return "????????????";
    }

    @RequestMapping("uploadCourseExcel")
    String uploadCourseExcel(@RequestParam MultipartFile excel) throws IOException {
        if (excel == null || excel.isEmpty()) {
            return "????????????";
        } else {
            List<Course> courses = EasyExcel.read(excel.getInputStream(), Course.class, null).sheet(0).doReadSync();
            mongoDao.insertCourses(courses);
        }
        return "????????????";
    }


    @RequestMapping("uploadTeacherCourseExcel")
    String uploadTeacherCourseExcel(@RequestParam MultipartFile excel) throws IOException {
        if (excel == null || excel.isEmpty()) {
            return "????????????";
        } else {
            List<TeacherCourse> teacherCourses = EasyExcel.read(excel.getInputStream(), TeacherCourse.class, null).sheet(0).doReadSync();
            mongoDao.insertTeacherCourseList(teacherCourses);
        }
        return "????????????";
    }

    @RequestMapping("uploadStudentCourseExcel")
    String uploadStudentCourseExcel(@RequestParam MultipartFile excel) throws IOException {
        if (excel == null || excel.isEmpty()) {
            return "????????????";
        } else {
            List<StudentCourse> studentCourses = EasyExcel.read(excel.getInputStream(), StudentCourse.class, null).sheet(0).doReadSync();
            mongoDao.insertStudentCourseList(studentCourses);
        }
        return "????????????";
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
            return "????????????";
        } catch (MongoException e) {
            e.printStackTrace();
            return "????????????";
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
            return "????????????";
        } catch (MongoException e) {
            e.printStackTrace();
            return "????????????";
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
            return "????????????";
        } catch (MongoException e) {
            e.printStackTrace();
            return "????????????";
        }
    }

    @RequestMapping("studentChooseCourse")
    List<Course> studentChooseCourse(@RequestParam String sid) {
        return mongoDao.selectChooseCourse(sid);
    }

    @RequestMapping("chooseCourse")
    boolean updateCourse(@RequestParam String sid, @RequestParam String cid) {
        return mongoDao.insertChooseCourse(sid, cid);
    }

    @RequestMapping("deleteChooseCourse")
    boolean deleteChooseCourse(@RequestParam String sid, @RequestParam String cid) {
        return mongoDao.deleteChooseCourse(sid, cid);
    }

    @RequestMapping("updateScore")
    boolean updateScore(@RequestParam String sid, @RequestParam String cid, @RequestParam double score) {
        return mongoDao.updateChooseCourse(sid, cid, score);
    }

    @RequestMapping("top10Students")
    List<StudentExtend> top10Students(){
        return mongoDao.topNStudents(10);
    }

    @RequestMapping("chooseCourseTop10Students")
    List<StudentExtend> chooseCourseTop10Students(){
        return mongoDao.chooseCourseTopNStudents(10);
    }

    @RequestMapping("courseChooseCountAndAvgScore")
    List<CourseExtend> courseChooseCountAndAvgScore(){
        return mongoDao.courseChooseCountAndAvgScore();
    }

    @RequestMapping("courseMaxScoreWithStudentName")
    List<CourseExtend> courseMaxScoreWithStudentName(){
        return mongoDao.courseMaxScoreWithStudentName();
    }
}
