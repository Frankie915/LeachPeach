package com.example.leachpeach.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "exercise_table")
public class Exercise implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int weight;
    private int reps;
    private int sets;

    @ColumnInfo(name = "workout_id")
    private long workoutId; // This field denotes the Workout that this Exercise belongs to

    public Exercise(String name, int weight, int reps, int sets) {
        this.name = name;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
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

    public int getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }
}
