package com.example.classattendance;

public class SubjectModel {
    private int id;
    private String name;

    public SubjectModel(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public SubjectModel() {
    }
    @Override
    public String toString() {
        return "UserModel{" + "id=" + id + ", name='" + name + '\'';
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
