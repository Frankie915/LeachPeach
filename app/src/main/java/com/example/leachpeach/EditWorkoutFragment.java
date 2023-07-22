package com.example.leachpeach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.model.Exercise;
import com.example.leachpeach.model.Workout;

import java.util.ArrayList;

public class EditWorkoutFragment extends Fragment {

    private RecyclerView exerciseRecyclerView;
    private EditExerciseAdapter editExerciseAdapter;
    private Workout workout;

    private static final String WORKOUT_KEY = "workout";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_workout, container, false);

        // Initialize the RecyclerView
        exerciseRecyclerView = view.findViewById(R.id.exerciseRecyclerView);

        if (getArguments() != null) {
            workout = (Workout) getArguments().getSerializable(WORKOUT_KEY);
        }

        // Initialize the EditExerciseAdapter
        editExerciseAdapter = new EditExerciseAdapter((ArrayList<Exercise>) workout.getExercises());
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Attach the EditExerciseAdapter to the RecyclerView
        exerciseRecyclerView.setAdapter(editExerciseAdapter);

        // Get the save button and set its onClickListener
        Button saveButton = view.findViewById(R.id.saveWorkoutButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new list of exercises
                ArrayList<Exercise> newExercises = new ArrayList<>();

                // For each item in the RecyclerView, get the data from the EditText views and create a new Exercise object
                for (int i = 0; i < editExerciseAdapter.getItemCount(); i++) {
                    View itemView = exerciseRecyclerView.getLayoutManager().findViewByPosition(i);

                    // Get the data from EditText views
                    String newName = ((EditText) itemView.findViewById(R.id.exerciseNameEditText)).getText().toString();
                    int newWeight = Integer.parseInt(((EditText) itemView.findViewById(R.id.weightEditText)).getText().toString());
                    int newReps = Integer.parseInt(((EditText) itemView.findViewById(R.id.repsEditText)).getText().toString());
                    int newSets = Integer.parseInt(((EditText) itemView.findViewById(R.id.setsEditText)).getText().toString());

                    // Create a new Exercise object with the new data
                    Exercise newExercise = new Exercise(newName, newWeight, newReps, newSets);

                    newExercises.add(newExercise);
                }

                // Update the workout with the new list of exercises
//                workout.setExercises(newExercises);

                // Notify the adapter that the data has changed
                editExerciseAdapter.notifyDataSetChanged();

                // Save the workout itself
                // You need to implement this part yourself. This could mean updating the workout in your database, etc.
            }
        });

        return view;
    }

    public static EditWorkoutFragment newInstance(Workout workout) {
        EditWorkoutFragment fragment = new EditWorkoutFragment();
        Bundle args = new Bundle();
        args.putSerializable(WORKOUT_KEY, workout);
        fragment.setArguments(args);
        return fragment;
    }
}
