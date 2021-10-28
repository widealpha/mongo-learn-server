package top.widealpha.mongodemo.bean;

import com.alibaba.excel.annotation.ExcelProperty;

public class Course {
    @ExcelProperty("课程号")
    String cid;
    @ExcelProperty("课程名")
    String name;
    @ExcelProperty("先行课程号")
    String fcid;
    @ExcelProperty("学分")
    Double credit;

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
