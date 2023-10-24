package com.example.courseprovider;

public class DTO_For_book {

    public DTO_For_book(String coursenam, String id, String subid) {
        this.coursenam = coursenam;
        this.id = id;
        this.subid = subid;
    }

    public String getCoursenam() {
        return coursenam;
    }

    public void setCoursenam(String coursenam) {
        this.coursenam = coursenam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    private String coursenam;
    private  String id;
    private  String subid;



}
