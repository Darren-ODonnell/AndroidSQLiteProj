package com.example.myapplication.Models;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Task {
    private int id;
    private String status = "incomplete";
    private String category;
    private String description;
    private Date deadline;

    public Task(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String s){
        this.status = s;
    }
    public void updateStatus() {
        switch (this.status){
            case "incomplete":
                this.status = "done";
                break;
            case "done":
                this.status = "incomplete";
                break;
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDeadlineAsMilliString() {
        //date to long
        long milliSecs = deadline.getTime();
        //long to String
        return Long.toString(milliSecs);
    }

    public void setDeadlineAsMillis(long millis){
        deadline = new Date(millis);
    }

    public String getDeadlineAsString() {
        return deadline.getDay() + "/" +
                deadline.getMonth() + "/" +
                deadline.getYear();
    }

    public void setDeadlineAsString(String s){
        //String to long
        String[] dateArr = s.split("/");
        for(String syt: dateArr){
            Log.d("syt", syt);
        }
        int days = Integer.parseInt(dateArr[0]);
        int months = Integer.parseInt(dateArr[1]);
        int years = Integer.parseInt(dateArr[2]);
        Date date = new Date(days, months, years);
        deadline = date;
    }
}
