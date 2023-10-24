package com.example.courseprovider;

public class Dataclass {
    public String getCoursenam() {
        return coursenam;
    }

    public void setCoursenam(String coursenam) {
        this.coursenam = coursenam;
    }

    private String coursenam;
    private String courseprovider;
    public Dataclass(String coursenam, String courseprovider) {
        this.coursenam = coursenam;
    }


}
