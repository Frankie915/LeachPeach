package com.example.leachpeach.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.leachpeach.model.Completion;
import com.example.leachpeach.model.Workout;
import com.example.leachpeach.repository.WorkoutRepository;

import java.util.Date;
import java.util.List;

public class CompletionViewModel extends AndroidViewModel {

    private WorkoutRepository repository;
    private LiveData<List<Completion>> allCompletions;

    public CompletionViewModel(@NonNull Application application) {
        super(application);
        repository = WorkoutRepository.getInstance(application.getApplicationContext());
        allCompletions = repository.getAllCompletions();
    }

    public LiveData<List<Completion>> getAllCompletions() {
        return allCompletions;
    }

    public LiveData<Completion> getCompletion(Date date) {
        return repository.getCompletion(date);
    }

    public void insertCompletion(Completion completion) {
        repository.insertCompletion(completion);
    }

}
