package top.widealpha.mongodemo.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import org.bson.BasicBSONObject;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.springframework.stereotype.Component;
import top.widealpha.mongodemo.bean.*;

import java.util.*;

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

    public List<Teacher> allTeachers() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("teacher");
        List<Teacher> teachers = new ArrayList<>();
        for (var item : collection.find()) {
            teachers.add(JSONObject.parseObject(item.toJson(), Teacher.class));
        }
        return teachers;
    }

    public List<Course> allCourses() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("course");
        List<Course> courses = new ArrayList<>();
        for (var item : collection.find()) {
            courses.add(JSONObject.parseObject(item.toJson(), Course.class));
        }
        return courses;
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

    // 插入学生课程关联列表
    public void insertStudentCourseList(List<StudentCourse> studentCourseList) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("student_course");
        List<Document> relationList = new ArrayList<>();
        for (StudentCourse sc : studentCourseList) {
            if (sc.getCid() == null || sc.getCid().isEmpty()) {
                continue;
            }
            relationList.add(Document.parse(JSON.toJSONString(sc)));
        }
        collection.insertMany(relationList);
    }

    // 插入学生老师关联列表
    public void insertTeacherCourseList(List<TeacherCourse> studentCourseList) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("teacher_course");
        List<Document> relationList = new ArrayList<>();
        for (TeacherCourse tc : studentCourseList) {
            if (tc.getCid() == null || tc.getCid().isEmpty()) {
                continue;
            }
            relationList.add(Document.parse(JSON.toJSONString(tc)));
        }
        collection.insertMany(relationList);
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
                Course course = JSON.parseObject(j.toJson(), Course.class);
                course.setScore(i.getDouble("score"));
                courses.add(course);
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

    // 删除课程
    public boolean deleteChooseCourse(String sid, String cid) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("student_course");
        BasicDBObject deleteObject = new BasicDBObject().append("cid", cid).append("sid", sid);
        collection.deleteOne(deleteObject);
        return true;
    }

    // 更新分数
    public boolean updateChooseCourse(String sid, String cid, double score) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("student_course");
        BasicDBObject oldObject = new BasicDBObject().append("cid", cid).append("sid", sid);
        collection.updateOne(oldObject, new BasicDBObject("$set", new BasicDBObject("score", score)));
        return true;
    }

    // 排名前n的同学
    public List<StudentExtend> topNStudents(int n) {
        List<StudentExtend> studentExtends = new ArrayList<>();
        MongoCollection<Document> studentCourseCollection = mongoDatabase.getCollection("student_course");
        MongoCollection<Document> studentCollection = mongoDatabase.getCollection("student");
        long time = System.currentTimeMillis();
        AggregateIterable<Document> aggregate = studentCourseCollection.aggregate(Arrays.asList(
                Aggregates.group("$sid", new BsonField("averageScore", new Document("$avg", "$score"))),
                Aggregates.sort(new Document("averageScore", -1))));
        int count = 1;
        for (var i : aggregate) {
            if (count++ > n) {
                break;
            }
            var documents = studentCollection.find(
                    new Document("sid", new Document("$eq", i.getString("_id"))));
            StudentExtend studentExtend = JSON.parseObject(documents.first().toJson(), StudentExtend.class);
            studentExtend.setScore(i.getDouble("averageScore"));
            studentExtends.add(studentExtend);

        }
        System.out.println("花费时间" + (System.currentTimeMillis() - time) + "ms");
        return studentExtends;
    }

    // 选课数目前n的同学
    public List<StudentExtend> chooseCourseTopNStudents(int n) {
        List<StudentExtend> studentExtends = new ArrayList<>();
        MongoCollection<Document> studentCourseCollection = mongoDatabase.getCollection("student_course");
        MongoCollection<Document> studentCollection = mongoDatabase.getCollection("student");
        long time = System.currentTimeMillis();
        AggregateIterable<Document> aggregate = studentCourseCollection.aggregate(Arrays.asList(
                Aggregates.group("$sid", new BsonField("courseCount", new Document("$sum", 1))),
                Aggregates.sort(new Document("courseCount", -1))));
        int count = 1;
        for (var i : aggregate) {
            if (count++ > n) {
                break;
            }
            var documents = studentCollection.find(
                    new Document("sid", new Document("$eq", i.getString("_id"))));
            StudentExtend studentExtend = JSON.parseObject(documents.first().toJson(), StudentExtend.class);
            studentExtend.setCourseCount(i.getInteger("courseCount"));
            studentExtends.add(studentExtend);

        }
        System.out.println("花费时间" + (System.currentTimeMillis() - time) + "ms");
        return studentExtends;
    }


    // 每节课的选课人数与平均成绩
    public List<CourseExtend> courseChooseCountAndAvgScore() {
        List<CourseExtend> courseExtends = new ArrayList<>();
        MongoCollection<Document> studentCourseCollection = mongoDatabase.getCollection("student_course");
        MongoCollection<Document> courseCollection = mongoDatabase.getCollection("course");
        long time = System.currentTimeMillis();
        // 平均成绩
        AggregateIterable<Document> aggregate = studentCourseCollection.aggregate(List.of(
                Aggregates.group("$cid", new BsonField("averageScore", new Document("$avg", "$score")),
                        new BsonField("chooseCount", new Document("$sum", 1)))));
        for (var i : aggregate) {
            var documents = courseCollection.find(
                    new Document("cid", new Document("$eq", i.getString("_id"))));
            CourseExtend courseExtend = JSON.parseObject(documents.first().toJson(), CourseExtend.class);
            courseExtend.setAverageScore(i.getDouble("averageScore"));
            courseExtend.setChooseCount(i.getInteger("chooseCount"));
            courseExtends.add(courseExtend);
        }
        System.out.println("花费时间" + (System.currentTimeMillis() - time) + "ms");
        return courseExtends;
    }

    //求每门课程最高成绩以及最高成绩对应的学生姓名
    public List<CourseExtend> courseMaxScoreWithStudentName() {
        List<CourseExtend> courseExtends = new ArrayList<>();
        MongoCollection<Document> studentCourseCollection = mongoDatabase.getCollection("student_course");
        MongoCollection<Document> courseCollection = mongoDatabase.getCollection("course");
        MongoCollection<Document> studentCollection = mongoDatabase.getCollection("student");
        long time = System.currentTimeMillis();
        // 平均成绩
        AggregateIterable<Document> aggregate = studentCourseCollection.aggregate(Arrays.asList(
                Aggregates.sort(new Document(new Document("score", 1))),
                Aggregates.group("$cid", new BsonField("maxScore", new Document("$max", "$score")),
                        new BsonField("doc", new Document("$first", new Document("sid", "$sid").append("maxScore", "$maxScore"))))));

        for (var i : aggregate) {
            var documents = courseCollection.find(
                    new Document("cid", new Document("$eq", i.getString("_id"))));
            CourseExtend courseExtend = JSON.parseObject(documents.first().toJson(), CourseExtend.class);

            String sid = (String) ((Map) i.get("doc")).get("sid");
            var documents1 = studentCollection.find(
                    new Document("sid", new Document("$eq", sid)));
            courseExtend.setMaxScore(i.getDouble("maxScore"));
            courseExtend.setMaxScoreStudentName(documents1.first().getString("name"));
            courseExtends.add(courseExtend);
        }
        System.out.println("花费时间" + (System.currentTimeMillis() - time) + "ms");
        return courseExtends;
    }
}
