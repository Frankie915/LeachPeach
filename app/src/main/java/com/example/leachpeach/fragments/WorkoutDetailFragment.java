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

import com.example.leachpeach.adapters.ExerciseDetailAdapter;
import com.example.leachpeach.viewmodel.MyViewModelFactory;
import com.example.leachpeach.R;
import com.example.leachpeach.model.Exercise;
import com.example.leachpeach.model.Workout;
import com.example.leachpeach.viewmodel.WorkoutViewModel;

public class WorkoutDetailFragment extends Fragment {
    private WorkoutViewModel workoutViewModel;
    private Button saveChangesButton;
    private ExerciseDetailAdapter adapter;
    private TextView workoutNameTextView;
    private RecyclerView recyclerView;
    private Button deleteWorkoutButton;
    private Workout currentWorkout;

    private EditText newExerciseName;
    private EditText newExerciseWeight;
    private EditText newExerciseSets;
    private EditText newExerciseReps;
    private Button addExerciseButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_detail_fragment, container, false);
        saveChangesButton = view.findViewById(R.id.save_changes_button);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
        workoutNameTextView = view.findViewById(R.id.workout_name);
        recyclerView = view.findViewById(R.id.recycler_view_exercises);
        deleteWorkoutButton = view.findViewById(R.id.delete_workout_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new ExerciseDetailAdapter();
        recyclerView.setAdapter(adapter);

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


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get workout ID from arguments and display workout details
        if (getArguments() != null) {
            int workoutId = getArguments().getInt("workoutId");
            workoutViewModel = new ViewModelProvider(requireActivity(), new MyViewModelFactory(requireActivity().getApplication())).get(WorkoutViewModel.class);
            workoutViewModel.getWorkout(workoutId).observe(getViewLifecycleOwner(), new Observer<Workout>() {
                @Override
                public void onChanged(Workout workout) {
                    // Check if the workout is not null
                    if(workout != null) {
                        // Update UI with the workout
                        displayWorkout(workout);
                        currentWorkout = workout;  // Update the current Workout
                    }
                }
            });
        }
    }

    private void displayWorkout(Workout workout) {
        workoutNameTextView.setText(workout.getName());
        adapter.setExercises(workout.getExercises());
    }

    private void saveChanges() {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            ExerciseDetailAdapter.ExerciseDetailHolder holder = (ExerciseDetailAdapter.ExerciseDetailHolder) recyclerView.findViewHolderForAdapterPosition(i);
            if (holder != null) {
                Exercise exercise = currentWorkout.getExercises().get(i);
                exercise.setName(holder.editTextName.getText().toString());
                exercise.setWeight(Integer.parseInt(holder.editTextWeight.getText().toString()));
                exercise.setSets(Integer.parseInt(holder.editTextSets.getText().toString()));
                exercise.setReps(Integer.parseInt(holder.editTextReps.getText().toString()));
            }
        }
        workoutViewModel.update(currentWorkout);
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private void addExercise() {
        String name = newExerciseName.getText().toString();
        int weight = Integer.parseInt(newExerciseWeight.getText().toString());
        int sets = Integer.parseInt(newExerciseSets.getText().toString());
        int reps = Integer.parseInt(newExerciseReps.getText().toString());

        Exercise exercise = new Exercise(name, weight, sets, reps);
        currentWorkout.getExercises().add(exercise);
        adapter.setExercises(currentWorkout.getExercises());

        newExerciseName.setText("");
        newExerciseWeight.setText("");
        newExerciseSets.setText("");
        newExerciseReps.setText("");
    }


}

