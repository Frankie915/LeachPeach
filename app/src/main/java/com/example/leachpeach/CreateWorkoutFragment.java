package com.example.leachpeach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.model.Exercise;
import com.example.leachpeach.model.Workout;
import com.example.leachpeach.viewmodel.WorkoutViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class CreateWorkoutFragment extends Fragment {

    private EditText exerciseNameEditText, exerciseWeightEditText, exerciseSetsEditText, exerciseRepsEditText;

    private EditText workoutNameEditText;
    private RecyclerView exerciseRecyclerView;
    private FloatingActionButton saveWorkoutButton; // This remains as FloatingActionButton
    private Button addExerciseButton; // This changes to Button
    private WorkoutViewModel workoutViewModel;
    private ExerciseAdapter exerciseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_workout, container, false);

        exerciseNameEditText = view.findViewById(R.id.exerciseNameEditText);
        exerciseWeightEditText = view.findViewById(R.id.exerciseWeightEditText);
        exerciseSetsEditText = view.findViewById(R.id.exerciseSetsEditText);
        exerciseRepsEditText = view.findViewById(R.id.exerciseRepsEditText);
        exerciseRecyclerView = view.findViewById(R.id.exerciseRecyclerView);
        saveWorkoutButton = view.findViewById(R.id.saveWorkoutButton);
        addExerciseButton = view.findViewById(R.id.addExerciseButton); // No more ClassCastException here

        workoutViewModel = new ViewModelProvider(this, new MyViewModelFactory(getActivity().getApplication())).get(WorkoutViewModel.class);

        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        exerciseAdapter = new ExerciseAdapter();
        exerciseRecyclerView.setAdapter(exerciseAdapter);

        saveWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkout();
            }
        });

        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExercise();
            }
        });

        return view;
    }

    private void saveWorkout() {
        // logic to save the workout to the database
        // Extract workout name from EditText
        String workoutName = workoutNameEditText.getText().toString().trim();

        // Create a new Workout object
        Workout workout = new Workout(workoutName, new Date(), exerciseAdapter.getExercises());

        // Use ViewModel to insert workout
        workoutViewModel.insertWorkout(workout);

        // Replace this fragment with MainFragment (or whatever is appropriate in your case)
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MainFragment())
                .commit();
    }

    private void addExercise() {
        // Read the input values
        String name = exerciseNameEditText.getText().toString().trim();
        String weightText = exerciseWeightEditText.getText().toString().trim();
        String setsText = exerciseSetsEditText.getText().toString().trim();
        String repsText = exerciseRepsEditText.getText().toString().trim();

        // Check if any field is empty
        if (name.isEmpty() || weightText.isEmpty() || setsText.isEmpty() || repsText.isEmpty()) {
            // Here you can display a message to the user that all fields must be filled
            return;
        }

        // If fields are not empty, continue with parsing and adding the exercise
        int weight = Integer.parseInt(weightText);
        int sets = Integer.parseInt(setsText);
        int reps = Integer.parseInt(repsText);

        // Create a new Exercise object with the input values
        Exercise exercise = new Exercise(name, weight, sets, reps);

        // Add the exercise to the RecyclerView
        exerciseAdapter.addExercise(exercise);

        // Clear the input fields
        exerciseNameEditText.setText("");
        exerciseWeightEditText.setText("");
        exerciseSetsEditText.setText("");
        exerciseRepsEditText.setText("");
    }

}
