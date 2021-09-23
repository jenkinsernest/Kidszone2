package com.example.androidebook.Item;

import java.io.Serializable;

public class SchoolList implements Serializable {

    private String cid, school_name, total_course, sch_image;

    public SchoolList(String cid, String category_name, String total_books, String cat_image) {
        this.cid = cid;
        this.school_name = category_name;
        this.total_course = total_books;
        this.sch_image=cat_image;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getTotal_course() {
        return total_course;
    }
     public void setTotal_course(String total_course) {
        this.total_course = total_course;
    }

    public String getsch_image() {
        return sch_image;
    }
    public void setsch_image(String sch_image) {
        this.sch_image = sch_image;
    }
}
