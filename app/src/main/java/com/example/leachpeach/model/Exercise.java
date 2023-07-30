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

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
