package com.example.leachpeach.repository;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.leachpeach.dao.CompletionDao;
import com.example.leachpeach.dao.ExerciseDao;
import com.example.leachpeach.dao.WorkoutDao;
import com.example.leachpeach.database.WorkoutDatabase;
import com.example.leachpeach.model.Completion;
import com.example.leachpeach.model.Exercise;
import com.example.leachpeach.model.Workout;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkoutRepository {

    private static final String DATABASE_NAME = "WorkoutDatabase.db";
    private WorkoutDao workoutDao;
    private ExerciseDao exerciseDao;

    private CompletionDao completionDao;

    private static WorkoutRepository mWorkoutRepo;
    private LiveData<List<Workout>> allWorkouts;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
/*
    public WorkoutRepository(WorkoutDatabase db) {
        workoutDao = db.workoutDao();
        exerciseDao = db.exerciseDao();
        allWorkouts = workoutDao.getAllWorkouts();
    }
*/
    private WorkoutRepository(Context context) {

        RoomDatabase.Callback databaseCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

            }
        };

        WorkoutDatabase database = Room.databaseBuilder(context, WorkoutDatabase.class, DATABASE_NAME)
                .addCallback(databaseCallback)
                .fallbackToDestructiveMigration()
                .build();

        exerciseDao = database.exerciseDao();
        workoutDao = database.workoutDao();
        completionDao = database.completionDao();
        allWorkouts = workoutDao.getAllWorkouts();
    }

    public static WorkoutRepository getInstance(Context context) {
        if (mWorkoutRepo == null) {
            mWorkoutRepo = new WorkoutRepository(context);
        }
        return mWorkoutRepo;
    }


    public LiveData<List<Workout>> getAllWorkouts() {
        return allWorkouts;
    }

    public void insert(Workout workout) {
        databaseWriteExecutor.execute(() -> {
            long workoutId = workoutDao.insert(workout);
            for (Exercise exercise : workout.getExercises()) {
                exercise.setWorkoutId(workoutId);
                exerciseDao.insert(exercise);
            }
        });
    }

    public void update(Workout workout) {
        databaseWriteExecutor.execute(() -> {
            workoutDao.update(workout);
            for (Exercise exercise : workout.getExercises()) {
                exercise.setWorkoutId(workout.getId());
                exerciseDao.update(exercise);
            }
        });
    }
    public void addExercise(Exercise exercise) {
        databaseWriteExecutor.execute(() -> {
            long exerciseId = exerciseDao.insert(exercise);
            exercise.setId(exerciseId);
        });
    }

    public void deleteExercise(Exercise exercise) {
        exerciseDao.delete(exercise);
    }
    public void delete(Workout workout) {
        databaseWriteExecutor.execute(() -> {
            for (Exercise exercise : workout.getExercises()) {
                exerciseDao.delete(exercise);
            }
            workoutDao.delete(workout);
        });
    }

    public LiveData<Workout> getWorkout(int id) {
        return workoutDao.getWorkout(id);
    }

    public LiveData<List<Exercise>> getExercises(long workoutId) {
        return exerciseDao.getExercises(workoutId);
    }

    public void insertCompletion(Completion completion) {
        databaseWriteExecutor.execute(() -> {
            completionDao.insert(completion);
        });
    }

    public LiveData<Completion> getCompletion(Date date) {
        return completionDao.getCompletion(date);
    }

    public LiveData<List<Completion>> getAllCompletions() {
        return completionDao.getAllCompletions();
    }

}
