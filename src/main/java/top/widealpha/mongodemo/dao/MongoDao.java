package top.widealpha.mongodemo.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Component;
import top.widealpha.mongodemo.bean.Course;
import top.widealpha.mongodemo.bean.Student;
import top.widealpha.mongodemo.bean.Teacher;

import java.util.ArrayList;
import java.util.List;

@Component
public class MongoDao {
    MongoDatabase mongoDatabase;

    public MongoDao(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public List<Student> allStudents() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("student");
        List<Student> students = new ArrayList<>();
        for (var item : collection.find()) {
            students.add(JSONObject.parseObject(item.toJson(), Student.class));
        }
        return students;
    }

    //
    public List<Student> findAgeLessStudents(int age) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("student");
        List<Student> students = new ArrayList<>();
        for (var item : collection.find(new Document("age", new Document("$lt", age)))) {
            students.add(JSONObject.parseObject(item.toJson(), Student.class));
        }
        return students;
    }

    //检索先行课号为“300001”的课程名
    public List<Course> findCourseWithFcid(String fcid) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("course");
        List<Course> courses = new ArrayList<>();
        for (var item : collection.find(new Document("fcid", new Document("$eq", fcid)))) {
            courses.add(JSONObject.parseObject(item.toJson(), Course.class));
        }
        return courses;
    }

    //找出所有在CS学院的老师
    public List<Teacher> findTeacherWithDname(String dname) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("teacher");
        List<Teacher> teachers = new ArrayList<>();
        for (var item : collection.find(new Document("dname", new Document("$eq", dname)))) {
            teachers.add(JSONObject.parseObject(item.toJson(), Teacher.class));
        }
        return teachers;
    }

    // 插入学生列表
    public void insertStudents(List<Student> students) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("student");
        List<Document> studentList = new ArrayList<>();
        for (Student s : students) {
            if (s.getSid() == null || s.getSid().isEmpty()) {
                continue;
            }
            studentList.add(Document.parse(JSON.toJSONString(s)));
        }
        collection.insertMany(studentList);
    }

    // 插入教师列表
    public void insertTeachers(List<Teacher> teachers) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("teacher");
        List<Document> teacherList = new ArrayList<>();
        for (Teacher s : teachers) {
            if (s.getTid() == null || s.getTid().isEmpty()) {
                continue;
            }
            teacherList.add(Document.parse(JSON.toJSONString(s)));
        }
        collection.insertMany(teacherList);
    }

    // 插入课程列表
    public void insertCourses(List<Course> courses) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("course");
        List<Document> courseList = new ArrayList<>();
        for (Course c : courses) {
            if (c.getCid() == null || c.getCid().isEmpty()) {
                continue;
            }
            courseList.add(Document.parse(JSON.toJSONString(c)));
        }
        collection.insertMany(courseList);
    }
}
