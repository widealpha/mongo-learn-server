package top.widealpha.mongodemo.bean;

import com.alibaba.excel.annotation.ExcelProperty;

public class Course {
    String _id;
    @ExcelProperty("CID")
    String cid;
    @ExcelProperty("NAME")
    String name;
    @ExcelProperty("FCID")
    String fcid;
    @ExcelProperty("CREDIT")
    Double credit;
    Double score;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFcid() {
        return fcid;
    }

    public void setFcid(String fcid) {
        this.fcid = fcid;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }
}
