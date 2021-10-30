package top.widealpha.mongodemo.bean;

import com.alibaba.excel.annotation.ExcelProperty;

public class Teacher {
    String _id;
    @ExcelProperty("教师编号")
    String tid;
    @ExcelProperty("教师姓名")
    String name;
    @ExcelProperty("性别")
    String sex;
    @ExcelProperty("年龄")
    Integer age;
    @ExcelProperty("院系名称")
    String dname;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }
}
