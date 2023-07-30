package com.example.leachpeach.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.leachpeach.viewmodel.WorkoutViewModel;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;

    public MyViewModelFactory(Application application) {
        mApplication = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WorkoutViewModel.class)) {
            return (T) new WorkoutViewModel(mApplication);
        } else if (modelClass.isAssignableFrom(ExerciseViewModel.class)) {
            return (T) new ExerciseViewModel(mApplication);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}

