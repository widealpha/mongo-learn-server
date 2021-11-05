package top.widealpha.mongodemo.bean;

public class StudentExtend extends Student{
    double score;
    int courseCount;

    public int getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(int courseCount) {
        this.courseCount = courseCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
