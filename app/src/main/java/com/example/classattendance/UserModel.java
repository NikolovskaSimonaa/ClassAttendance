package com.example.classattendance;
public class UserModel {
    private int id;
    private String name;
    private String surname;
    private String professor; // "true" - the user is professor, "false" - the user is student
    private String email;
    private String password;

    public UserModel(int id, String name, String surname, String professor, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.professor = professor;
        this.email = email;
        this.password = password;
    }

    public UserModel() {
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", professor='" + professor + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
