package com.example.leachpeach.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.leachpeach.model.Exercise;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    long insert(Exercise exercise);

    @Update
    void update(Exercise exercise);

    @Delete
    void delete(Exercise exercise);

    @Query("DELETE FROM exercise_table")
    void deleteAllExercises();

    @Query("SELECT * FROM exercise_table ORDER BY name")
    LiveData<List<Exercise>> getAllExercises();

    @Query("SELECT distinct * FROM exercise_table GROUP BY name ORDER BY name DESC")
    LiveData<List<Exercise>> getExerciseSet();

    @Query("SELECT * FROM exercise_table WHERE workout_id = :workoutId")
    LiveData<List<Exercise>> getExercisesOfWorkout(long workoutId);

    @Query("SELECT * FROM exercise_table WHERE id = :id")
    LiveData<Exercise> getExercise(int id);
}
