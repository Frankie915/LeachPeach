package com.example.leachpeach.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.leachpeach.util.DateConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "workout_table")
public class Workout implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    @TypeConverters(DateConverter.class)
    private Date date;

    private ArrayList<Exercise> exercises;

    public Workout(String name, Date date, ArrayList<Exercise> exercises) {
        this.name = name;
        this.date = date;
        this.exercises = exercises;
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

    public Date getDate() {
        return date;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
