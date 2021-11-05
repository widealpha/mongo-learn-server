package top.widealpha.mongodemo.bean;

public class CourseExtend extends Course{
    int chooseCount;
    double averageScore;
    double maxScore;
    String maxScoreStudentName;

    public int getChooseCount() {
        return chooseCount;
    }

    public void setChooseCount(int chooseCount) {
        this.chooseCount = chooseCount;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public String getMaxScoreStudentName() {
        return maxScoreStudentName;
    }

    public void setMaxScoreStudentName(String maxScoreStudentName) {
        this.maxScoreStudentName = maxScoreStudentName;
    }
}
