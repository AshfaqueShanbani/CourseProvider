package com.example.courseprovider;

public class DTO {

    private String coursenam;
    private String id;
    private String imageuri; // Add this property

    public DTO(String coursenam, String id, String imageuri) {
        this.coursenam = coursenam;
        this.id = id;
        this.imageuri = imageuri; // Initialize the image URL
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

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
}
