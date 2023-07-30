package com.example.leachpeach.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.example.leachpeach.model.Exercise;
import java.util.List;
import com.example.leachpeach.repository.WorkoutRepository;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private WorkoutRepository mWorkoutRepo;
    private final MutableLiveData<Long> mWorkoutIdLiveData = new MutableLiveData<>();

    public LiveData<List<Exercise>> exerciseListLiveData =
            Transformations.switchMap(mWorkoutIdLiveData, workoutId ->
                    mWorkoutRepo.getExercises(workoutId));

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        mWorkoutRepo = WorkoutRepository.getInstance(application.getApplicationContext());
    }

    public void addExercise(Exercise exercise) { mWorkoutRepo.addExercise(exercise);}

    public void deleteExercise(Exercise exercise) {
        mWorkoutRepo.deleteExercise(exercise);
    }

    public void loadExercises(long workoutId) {
        mWorkoutIdLiveData.setValue(workoutId);
    }
}
