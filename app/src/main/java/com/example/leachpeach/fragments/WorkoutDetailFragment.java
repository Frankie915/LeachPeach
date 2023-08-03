package com.example.leachpeach.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.adapters.ExerciseViewAdapter;
import com.example.leachpeach.viewmodel.MyViewModelFactory;
import com.example.leachpeach.R;
import com.example.leachpeach.model.Exercise;
import com.example.leachpeach.model.Workout;
import com.example.leachpeach.viewmodel.ExerciseViewModel;
import com.example.leachpeach.viewmodel.WorkoutViewModel;
import androidx.navigation.Navigation;


import java.util.List;

public class WorkoutDetailFragment extends Fragment {
    private WorkoutViewModel workoutViewModel;

    private List<Exercise> mExerciseList;
    private ExerciseViewModel exerciseViewModel;
    private Button saveChangesButton;
    private ExerciseViewAdapter adapter;
    private TextView workoutNameTextView;
    private RecyclerView recyclerView;
    private Button deleteWorkoutButton;
    private Workout currentWorkout;

    private EditText newExerciseName;
    private EditText newExerciseWeight;
    private EditText newExerciseSets;
    private EditText newExerciseReps;
    private Button addExerciseButton;

    private long mWorkoutId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_detail, container, false);
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

        adapter = new ExerciseViewAdapter();
        recyclerView.setAdapter(adapter);

        deleteWorkoutButton.setOnClickListener(new View.OnClickListener() {  // Set the onClick listener
            @Override
            public void onClick(View v) {
                if (currentWorkout != null) {
                    workoutViewModel.delete(currentWorkout);  // Delete the current Workout
                    if (getView() != null) {
                        Navigation.findNavController(getView()).navigateUp();
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
            mWorkoutId = (long)workoutId;
            workoutViewModel = new ViewModelProvider(requireActivity(), new MyViewModelFactory(requireActivity().getApplication())).get(WorkoutViewModel.class);
            exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);

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
            ExerciseViewAdapter.ExerciseViewHolder holder = (ExerciseViewAdapter.ExerciseViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            if (holder != null) {
                Exercise exercise = currentWorkout.getExercises().get(i);
                exercise.setName(holder.textViewName.getText().toString());
                exercise.setWeight(Integer.parseInt(holder.textViewWeight.getText().toString()));
                exercise.setSets(Integer.parseInt(holder.textViewSets.getText().toString()));
                exercise.setReps(Integer.parseInt(holder.textViewReps.getText().toString()));
            }
        }
        workoutViewModel.update(currentWorkout);
        if (getView() != null) {
            Navigation.findNavController(getView()).navigateUp();
        }
    }

    private void addExercise() {
        String name = newExerciseName.getText().toString().trim();
        String weightStr = newExerciseWeight.getText().toString().trim();
        String setsStr = newExerciseSets.getText().toString().trim();
        String repsStr = newExerciseReps.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(setsStr) || TextUtils.isEmpty(repsStr)) {
            Toast.makeText(getActivity(), "Please complete all fields for exercise", Toast.LENGTH_SHORT).show();
            return;
        }

        int weight = Integer.parseInt(weightStr);
        int sets = Integer.parseInt(setsStr);
        int reps = Integer.parseInt(repsStr);

        Exercise exercise = new Exercise(name, weight, reps, sets);
        currentWorkout.getExercises().add(exercise);
        exercise.setWorkoutId(mWorkoutId);
        exerciseViewModel.addExercise(exercise);
        //workoutViewModel.insertExercise(exercise);
        adapter.setExercises(currentWorkout.getExercises());

        newExerciseName.setText("");
        newExerciseWeight.setText("");
        newExerciseSets.setText("");
        newExerciseReps.setText("");
    }
}

