package com.example.classattendance;

public class ClassModel {
    private int id;
    private String title;
    private String startTimestamp;
    private String endTimestamp;

    public ClassModel(int id, String title, String start, String end) {
        this.id = id;
        this.title = title;
        this.startTimestamp = start;
        this.endTimestamp = end;
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
    public String getStartTimestamp() {
        return startTimestamp;
    }
    public void setStartTimestamp(String t) {
        this.startTimestamp = t;
    }
    public String getEndTimestamp() {
        return endTimestamp;
    }
    public void setEndTimestamp(String t) {
        this.endTimestamp = t;
    }
}
