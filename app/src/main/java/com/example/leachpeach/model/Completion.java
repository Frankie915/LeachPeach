package com.example.leachpeach.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.leachpeach.util.DateConverter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "completion_table")
public class Completion implements Serializable {

    @PrimaryKey
    @TypeConverters(DateConverter.class)
    private Date date;

    private List<String> workouts;

    public Completion(Date date, List<String> workouts) {
        this.date = date;
        this.workouts = workouts;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<String> workouts) {
        this.workouts = workouts;
    }
}
