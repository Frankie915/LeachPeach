package com.example.leachpeach.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.leachpeach.dao.ExerciseDao;
import com.example.leachpeach.dao.WorkoutDao;
import com.example.leachpeach.database.WorkoutDatabase;
import com.example.leachpeach.model.Exercise;
import com.example.leachpeach.model.Workout;

import java.util.List;

public class WorkoutRepository {

    private WorkoutDao workoutDao;
    private ExerciseDao exerciseDao;
    private LiveData<List<Workout>> allWorkouts;

    public WorkoutRepository(WorkoutDatabase db) {
        workoutDao = db.workoutDao();
        exerciseDao = db.exerciseDao();
        allWorkouts = workoutDao.getAllWorkouts();
    }


    public LiveData<List<Workout>> getAllWorkouts() {
        return allWorkouts;
    }

    public void insert(Workout workout) {
        WorkoutDatabase.databaseWriteExecutor.execute(() -> {
            long workoutId = workoutDao.insert(workout);
            for (Exercise exercise : workout.getExercises()) {
                exercise.setWorkoutId(workoutId);
                exerciseDao.insert(exercise);
            }
        });
    }

    public void update(Workout workout) {
        WorkoutDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.update(workout);
            for (Exercise exercise : workout.getExercises()) {
                exercise.setWorkoutId(workout.getId());
                exerciseDao.update(exercise);
            }
        });
    }

    public void delete(Workout workout) {
        WorkoutDatabase.databaseWriteExecutor.execute(() -> {
            for (Exercise exercise : workout.getExercises()) {
                exerciseDao.delete(exercise);
            }
            workoutDao.delete(workout);
        });
    }

    public LiveData<Workout> getWorkout(int id) {
        return workoutDao.getWorkout(id);
    }

}
