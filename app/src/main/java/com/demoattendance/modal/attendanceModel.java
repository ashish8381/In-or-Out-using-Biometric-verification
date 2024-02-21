package com.demoattendance.modal;

public class attendanceModel {
    private int id;
    private String in_timestamp;

    public attendanceModel(){

    }

    public attendanceModel(int id, String in_timestamp) {
        this.id = id;
        this.in_timestamp = in_timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIn_timestamp() {
        return in_timestamp;
    }

    public void setIn_timestamp(String in_timestamp) {
        this.in_timestamp = in_timestamp;
    }
}
