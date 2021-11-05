package top.widealpha.mongodemo.bean;

import com.alibaba.excel.annotation.ExcelProperty;

public class TeacherCourse {
    String _id;
    @ExcelProperty("TID")
    String tid;
    @ExcelProperty("CID")
    String cid;

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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
