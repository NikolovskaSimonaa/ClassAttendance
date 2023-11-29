package com.example.classattendance;

public class ClassModel {
    private int id;
    private String title;
    private String timestamp;

    public ClassModel(int id, String title, String timestamp) {
        this.id = id;
        this.title = title;
        this.timestamp = timestamp;
    }

    public ClassModel() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String t) {
        this.timestamp = timestamp;
    }
}
