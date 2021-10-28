package top.widealpha.mongodemo.bean;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
    @ExcelProperty("学号")
    String sid;
    @ExcelProperty("姓名")
    String name;
    @ExcelProperty("性别")
    String sex;
    @ExcelProperty("年龄")
    Integer age;
    @ExcelProperty("出生日期")
    String birthday;
    @ExcelProperty("院系名称")
    String dname;
    @JsonProperty("class")
    @JSONField(name = "class")
    @ExcelProperty("班级")
    String className;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", birthday='" + birthday + '\'' +
                ", dname='" + dname + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
