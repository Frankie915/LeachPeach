package com.example.leachpeach.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.R;
import com.example.leachpeach.adapters.CompletionAdapter;
import com.example.leachpeach.model.Completion;
import com.example.leachpeach.model.Workout;
import com.example.leachpeach.viewmodel.CompletionViewModel;
import com.example.leachpeach.viewmodel.WorkoutViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgressFragment extends Fragment {

    private Spinner spinnerWorkouts;
    private Button buttonCompleteWorkout;
    private RecyclerView recyclerViewCompletions;
    private CompletionViewModel completionViewModel;
    private WorkoutViewModel workoutViewModel;
    private List<Workout> workoutList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerWorkouts = view.findViewById(R.id.spinner_workouts);
        buttonCompleteWorkout = view.findViewById(R.id.button_complete_workout);
        recyclerViewCompletions = view.findViewById(R.id.recycler_view_completions);

        workoutViewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);
        completionViewModel = new ViewModelProvider(this).get(CompletionViewModel.class);

        ArrayAdapter<Workout> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, workoutList);
        spinnerWorkouts.setAdapter(adapter);

        buttonCompleteWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Workout selectedWorkout = (Workout) spinnerWorkouts.getSelectedItem();
                if (selectedWorkout != null) {
                    Log.d("ProgressFragment", "Workout selected: " + selectedWorkout.getName());
                    List<String> workouts = new ArrayList<>();
                    workouts.add(selectedWorkout.getName());
                    Completion newCompletion = new Completion(new Date(), workouts);
                    completionViewModel.insertCompletion(newCompletion);
                } else {
                    Log.d("ProgressFragment", "No workout selected");
                }
            }
        });

        CompletionAdapter completionAdapter = new CompletionAdapter();
        recyclerViewCompletions.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCompletions.setAdapter(completionAdapter);

        workoutViewModel.getAllWorkouts().observe(getViewLifecycleOwner(), new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workouts) {
                Log.d("ProgressFragment", "Workouts changed: " + workouts);
                workoutList.clear();
                workoutList.addAll(workouts);
                adapter.notifyDataSetChanged();
            }
        });

        completionViewModel.getAllCompletions().observe(getViewLifecycleOwner(), new Observer<List<Completion>>() {
            @Override
            public void onChanged(List<Completion> completions) {
                completionAdapter.setCompletions(completions);
            }
        });
    }
}
