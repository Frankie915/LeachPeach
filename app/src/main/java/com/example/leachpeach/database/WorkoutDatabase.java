package com.example.leachpeach.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.leachpeach.dao.CompletionDao;
import com.example.leachpeach.dao.ExerciseDao;
import com.example.leachpeach.dao.WorkoutDao;
import com.example.leachpeach.model.Completion;
import com.example.leachpeach.model.Exercise;
import com.example.leachpeach.model.Workout;
import com.example.leachpeach.util.DataConverter;
import com.example.leachpeach.util.DateConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Workout.class, Exercise.class, Completion.class}, version = 5)
@TypeConverters({DataConverter.class, DateConverter.class})
public abstract class WorkoutDatabase extends RoomDatabase {

    private static volatile WorkoutDatabase INSTANCE;

    // Executor service with a fixed thread pool that you will use to run database operations
    // asynchronously on a background thread.


    public abstract WorkoutDao workoutDao();

    public abstract ExerciseDao exerciseDao();

    public abstract CompletionDao completionDao();

    public static WorkoutDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (WorkoutDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    WorkoutDatabase.class, "WorkoutDatabase.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
