package com.example.leachpeach.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.leachpeach.database.WorkoutDatabase;
import com.example.leachpeach.model.Exercise;
import com.example.leachpeach.model.Workout;
import com.example.leachpeach.repository.WorkoutRepository;

import java.util.List;

public class WorkoutViewModel extends AndroidViewModel {

    private WorkoutRepository repository;
    private LiveData<List<Workout>> allWorkouts;

    private final MutableLiveData<Long> mWorkoutIdLiveData = new MutableLiveData<>();

    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        repository = WorkoutRepository.getInstance(application.getApplicationContext());
        allWorkouts = repository.getAllWorkouts();
    }

    public LiveData<List<Workout>> getAllWorkouts() {
        return allWorkouts;
    }

    public LiveData<Workout> getWorkout(int id) {
        return repository.getWorkout(id);
    }


    public void insertWorkout(Workout workout) {
        repository.insert(workout);
    }


    public void update(Workout workout) {
        repository.update(workout);
    }

    public void delete(Workout workout) {
        repository.delete(workout);
    }

    public void insertExercise(Exercise exercise) {repository.addExercise(exercise);}
}
