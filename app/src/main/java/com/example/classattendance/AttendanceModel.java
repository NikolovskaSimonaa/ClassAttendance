package com.example.classattendance;

public class AttendanceModel {
    private int id;
    private String date;
    private Integer attendanceStatus;

    public AttendanceModel(int id, String date, Integer attendanceStatus) {
        this.id = id;
        this.date = date;
        this.attendanceStatus = attendanceStatus;
    }

    public AttendanceModel() {
    }

    @Override
    public String toString() {
        return "UserModel{" + "id=" + id + ", date=" + date +", attendance_status=" + attendanceStatus + '\'';
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Integer getAttendanceStatus() {
        return attendanceStatus;
    }
    public void setAttendanceStatus(Integer attendanceStatus) { this.attendanceStatus = attendanceStatus;}
}
