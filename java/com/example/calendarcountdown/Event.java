package com.example.calendarcountdown;

/**
 * Created by mac on 11/20/19.
 */

public class Event {

    private String name;
    private String date;
    private String time;

    Event(String name, String date, String time){
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName(){
        return this.name;
    }

    public String getDate(){
        return this.date;
    }

    public String getTime(){
        return this.time;
    }
}
