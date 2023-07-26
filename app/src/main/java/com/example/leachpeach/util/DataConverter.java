package com.example.leachpeach.util;

import androidx.room.TypeConverter;

import com.example.leachpeach.model.Exercise;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
}
