package com.example.leachpeach.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "exercise_table", foreignKeys = @ForeignKey(entity = Workout.class, parentColumns = "id",
        childColumns = "workout_id", onDelete = CASCADE))
public class Exercise implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    private String name;
    private String weight;
    private String reps;
    private String sets;

    @ColumnInfo(name = "workout_id")
    private long workoutId; // This field denotes the Workout that this Exercise belongs to

    public Exercise(String name, String weight, String reps, String sets) {
        this.name = name;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public String getReps() {
        return reps;
    }

    public String getSets() {
        return sets;
    }

    public long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }
}
