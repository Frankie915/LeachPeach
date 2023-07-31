package com.example.leachpeach.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.leachpeach.database.WorkoutDatabase;
import com.example.leachpeach.model.Exercise;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import java.util.List;

import com.example.leachpeach.repository.WorkoutRepository;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private WorkoutRepository mWorkoutRepo;
    private LiveData<List<Exercise>> allExercises;
    private LiveData<List<Exercise>> exerciseSet;
    private final MutableLiveData<Long> mWorkoutIdLiveData = new MutableLiveData<>();

    public LiveData<List<Exercise>> exerciseListLiveData =
            Transformations.switchMap(mWorkoutIdLiveData, workoutId ->
                    mWorkoutRepo.getExercises(workoutId));

    public ExerciseViewModel(@NonNull Application application) {

        super(application);
        WorkoutDatabase db = WorkoutDatabase.getInstance(application);
        mWorkoutRepo = WorkoutRepository.getInstance(application.getApplicationContext());
        allExercises = mWorkoutRepo.getAllExercises();
        exerciseSet = mWorkoutRepo.getExerciseSet();
    }

    public void addExercise(Exercise exercise) { mWorkoutRepo.addExercise(exercise);}
    public void deleteExercise(Exercise exercise) { mWorkoutRepo.deleteExercise(exercise); }
    public void loadExercises(long workoutId) { mWorkoutIdLiveData.setValue(workoutId); }
    public LiveData<List<Exercise>> getAllExercises() { return allExercises; }
    public LiveData<List<Exercise>> getExerciseSet() { return exerciseSet; }
    public LiveData<Exercise> getExercise(int id) { return mWorkoutRepo.getExercise(id); }

}
