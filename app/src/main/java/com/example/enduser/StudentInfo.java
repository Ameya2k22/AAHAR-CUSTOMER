package com.example.enduser;

public class StudentInfo {
    public String studentID;
    public long dayRemaining;

    public StudentInfo(){}

    public StudentInfo(String studentID, long dayRemaining) {
        this.studentID = studentID;
        this.dayRemaining = dayRemaining;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public long getDayRemaining() {
        return dayRemaining;
    }

    public void setDayRemaining(long dayRemaining) {
        this.dayRemaining = dayRemaining;
    }
}
