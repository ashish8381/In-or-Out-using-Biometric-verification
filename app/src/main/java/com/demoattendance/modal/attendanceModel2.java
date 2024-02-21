package com.demoattendance.modal;

public class attendanceModel2 {
    private int id;
    private String in_timestamp;

    private String out_timestamp;



    public attendanceModel2(){

    }

    public attendanceModel2(int id, String in_timestamp,String out_timestamp) {
        this.id = id;
        this.in_timestamp = in_timestamp;
        this.out_timestamp = out_timestamp;
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

    public String getOut_timestamp() {
        return out_timestamp;
    }

    public void setOut_timestamp(String out_timestamp) {
        this.out_timestamp = out_timestamp;
    }
}
