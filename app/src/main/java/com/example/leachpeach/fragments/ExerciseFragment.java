package com.example.leachpeach.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.R;
import com.example.leachpeach.adapters.ExerciseAdapter;
import com.example.leachpeach.adapters.ExerciseDetailAdapter;
import com.example.leachpeach.adapters.WorkoutAdapter;
import com.example.leachpeach.model.Exercise;
import com.example.leachpeach.model.Workout;
import com.example.leachpeach.viewmodel.ExerciseViewModel;
import com.example.leachpeach.viewmodel.MyViewModelFactory;
import com.example.leachpeach.viewmodel.WorkoutViewModel;

import java.util.List;


public class ExerciseFragment extends Fragment {

    private ExerciseViewModel exerciseViewModel;
    private ExerciseAdapter exerciseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        /*
        // No save changes button
        saveChangesButton = view.findViewById(R.id.save_changes_button);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Get list of workouts with the same name")
            }
        });
         */

        /*
        workoutNameTextView = view.findViewById(R.id.workout_name);
        recyclerView = view.findViewById(R.id.recycler_view_exercises);
        //deleteWorkoutButton = view.findViewById(R.id.delete_workout_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new ExerciseDetailAdapter();
        recyclerView.setAdapter(adapter);
         */

        /*
        // No delete button
        deleteWorkoutButton.setOnClickListener(new View.OnClickListener() {  // Set the onClick listener
            @Override
            public void onClick(View v) {
                if (currentWorkout != null) {
                    workoutViewModel.delete(currentWorkout);  // Delete the current Workout
                    if (getActivity() != null) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                }
            }
        });

         */

        /*
        // No new exercises
        newExerciseName = view.findViewById(R.id.new_exercise_name);
        newExerciseWeight = view.findViewById(R.id.new_exercise_weight);
        newExerciseSets = view.findViewById(R.id.new_exercise_sets);
        newExerciseReps = view.findViewById(R.id.new_exercise_reps);
        addExerciseButton = view.findViewById(R.id.add_exercise_button);

        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExercise();
            }
        });
         */

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel and Adapter
        exerciseViewModel = new ViewModelProvider(requireActivity(), new MyViewModelFactory(requireActivity().getApplication())).get(ExerciseViewModel.class);
        exerciseAdapter = new ExerciseAdapter();

        // Setup RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(exerciseAdapter);

        // Observe exercises LiveData
        exerciseViewModel.getAllExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                exerciseAdapter.setExercises(exercises);
            }
        });
    }
}

