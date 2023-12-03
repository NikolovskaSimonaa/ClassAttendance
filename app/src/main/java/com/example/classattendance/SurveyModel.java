package com.example.classattendance;

public class SurveyModel {
    private int id;
    private String grade;
    private String comment;

    public SurveyModel(int id, String grade, String comment) {
        this.id = id;
        this.grade = grade;
        this.comment = comment;
    }

    public SurveyModel() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String g) {
        this.grade = g;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String c) {
        this.comment = c;
    }
}
