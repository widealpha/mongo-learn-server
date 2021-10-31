package top.widealpha.mongodemo.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Component;
import top.widealpha.mongodemo.bean.Course;
import top.widealpha.mongodemo.bean.Student;
import top.widealpha.mongodemo.bean.StudentCourse;
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

    //找出年龄小于20岁的所有学生
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

    // 插入课程列表
    public void insertStudentCourseList(List<StudentCourse> studentCourseList) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("student_course");
    }

    // 更新student数据
    public void updateStudent(List<Student> students) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("student");
        for (Student s : students) {
            BasicDBObject dbObject = BasicDBObject.parse(JSON.toJSONString(s));
            collection.updateOne(new Document("sid", s.getSid()), new BasicDBObject("$set", dbObject));
        }
    }

    // 更新teacher数据
    public void updateTeacher(List<Teacher> teachers) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("teacher");
        for (Teacher t : teachers) {
            BasicDBObject dbObject = BasicDBObject.parse(JSON.toJSONString(t));
            collection.updateOne(new BasicDBObject("tid", t.getTid()), new BasicDBObject("$set", dbObject));
        }
    }

    // 更新course数据
    public void updateCourse(List<Course> courses) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("course");
        for (Course c : courses) {
            BasicDBObject dbObject = BasicDBObject.parse(JSON.toJSONString(c));
            collection.updateOne(new Document("cid", c.getCid()), new BasicDBObject("$set", dbObject));
        }
    }

    // 查询学生所选课程
    public List<Course> selectChooseCourse(String sid) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("student_course");
        MongoCollection<Document> courseCollection = mongoDatabase.getCollection("course");
        List<Course> courses = new ArrayList<>();
        for (Document i : collection.find(new Document("sid", new Document("$eq", sid)))) {
            for (Document j : courseCollection.find(new Document("cid", new Document("$eq", i.getString("cid"))))) {
                courses.add(JSON.parseObject(j.toJson(), Course.class));
            }
        }
        return courses;
    }


    public boolean insertChooseCourse(String sid, String cid) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("student_course");
        MongoCollection<Document> teacherCourseCollection = mongoDatabase.getCollection("teacher_course");
        String tid;
        Document document = teacherCourseCollection.find(new Document("cid", new Document("$eq", cid))).first();
        if (document == null) {
            tid = null;
        } else {
            tid = document.getString("tid");
        }
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCid(cid);
        studentCourse.setTid(tid);
        studentCourse.setSid(sid);
        collection.insertOne(Document.parse(JSON.toJSONString(studentCourse)));
        return true;
    }

    public boolean deleteChooseCourse(String sid, String cid) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("student_course");
        BasicDBObject deleteObject = new BasicDBObject().append("cid", cid).append("sid", sid);
        collection.deleteOne(deleteObject);
        return true;
    }

    public boolean updateChooseCourse(String sid, String cid, double score) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("student_course");
        BasicDBObject oldObject = new BasicDBObject().append("cid", cid).append("sid", sid);
        collection.updateOne(oldObject, new BasicDBObject("$set", new BasicDBObject("score", score)));
        return true;
    }
}
