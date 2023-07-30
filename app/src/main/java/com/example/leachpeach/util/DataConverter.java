package com.example.leachpeach.util;

import androidx.room.TypeConverter;

import com.example.leachpeach.model.Exercise;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataConverter {

    @TypeConverter
    public String fromExerciseList(ArrayList<Exercise> exercises) {
        if (exercises == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Exercise>>() {}.getType();
        return gson.toJson(exercises, type);
    }

    @TypeConverter
    public ArrayList<Exercise> toExerciseList(String exerciseString) {
        if (exerciseString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Exercise>>() {}.getType();
        return gson.fromJson(exerciseString, type);
    }

    @TypeConverter
    public String fromWorkoutList(List<String> workouts) {
        if (workouts == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.toJson(workouts, type);
    }

    @TypeConverter
    public List<String> toWorkoutList(String workoutString) {
        if (workoutString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(workoutString, type);
    }

}
