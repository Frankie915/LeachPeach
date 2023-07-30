package com.example.leachpeach;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.model.Exercise;
import com.example.leachpeach.model.Workout;
import com.example.leachpeach.repository.WorkoutRepository;
import com.example.leachpeach.viewmodel.WorkoutViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateWorkoutFragment extends Fragment {
    private WorkoutViewModel workoutViewModel;

    private EditText editTextWorkoutName;
    private EditText newExerciseName;
    private EditText newExerciseWeight;
    private EditText newExerciseSets;
    private EditText newExerciseReps;
    private Button addExerciseButton;
    private Button buttonSaveWorkout;
    private ExerciseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_workout, container, false);
        workoutViewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);
        editTextWorkoutName = view.findViewById(R.id.edit_text_workout_name);
        newExerciseName = view.findViewById(R.id.new_exercise_name);
        newExerciseWeight = view.findViewById(R.id.new_exercise_weight);
        newExerciseSets = view.findViewById(R.id.new_exercise_sets);
        newExerciseReps = view.findViewById(R.id.new_exercise_reps);
        addExerciseButton = view.findViewById(R.id.button_add_exercise);
        buttonSaveWorkout = view.findViewById(R.id.button_save_workout);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_exercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);

        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExercise();
            }
        });

        buttonSaveWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // retrieve data from the EditTexts and save the workout
                saveWorkout();
            }
        });

        return view;
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

        Exercise exercise = new Exercise(name, weight, sets, reps);
        adapter.getExercises().add(exercise);
        adapter.notifyDataSetChanged();

        newExerciseName.setText("");
        newExerciseWeight.setText("");
        newExerciseSets.setText("");
        newExerciseReps.setText("");
    }

    private void saveWorkout() {
        String workoutName = editTextWorkoutName.getText().toString().trim();
        List<Exercise> exercises = adapter.getExercises();


        if (TextUtils.isEmpty(workoutName) || exercises.isEmpty()) {
            Toast.makeText(getActivity(), "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Workout workout = new Workout(workoutName, new Date(), (ArrayList<Exercise>) exercises);

        workoutViewModel.insertWorkout(workout);
        MainFragment mainFragment = new MainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mainFragment);
        transaction.addToBackStack(null);  // this allows the user to navigate back to CreateWorkoutFragment
        transaction.commit();
    }
}
