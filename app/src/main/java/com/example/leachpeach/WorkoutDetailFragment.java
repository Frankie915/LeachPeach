package com.example.leachpeach;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leachpeach.model.Exercise;
import com.example.leachpeach.model.Workout;

import java.util.ArrayList;

public class WorkoutDetailFragment extends Fragment {

    private RecyclerView exerciseRecyclerView;
    private ExerciseAdapter exerciseAdapter;
    private Workout workout;

    private static final String WORKOUT_KEY = "workout";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_detail, container, false);

        // Initialize the RecyclerView
        exerciseRecyclerView = view.findViewById(R.id.exerciseRecyclerView);

        if (getArguments() != null) {
            workout = (Workout) getArguments().getSerializable(WORKOUT_KEY);
        }

        // Initialize the ExerciseAdapter with null ExerciseInteraction
        exerciseAdapter = new ExerciseAdapter();
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Attach the ExerciseAdapter to the RecyclerView
        exerciseRecyclerView.setAdapter(exerciseAdapter);

        return view;
    }

    public static WorkoutDetailFragment newInstance(Workout workout) {
        WorkoutDetailFragment fragment = new WorkoutDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(WORKOUT_KEY, workout);
        fragment.setArguments(args);
        return fragment;
    }
}
