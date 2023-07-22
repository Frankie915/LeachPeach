package com.example.leachpeach;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leachpeach.model.Exercise;
import com.example.leachpeach.model.Workout;
import com.example.leachpeach.viewmodel.WorkoutViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainFragment extends Fragment implements WorkoutAdapter.OnWorkoutClickListener {

    private RecyclerView workoutRecyclerView;
    private WorkoutViewModel workoutViewModel;
    private FloatingActionButton fabInsertWorkout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize the RecyclerView
        workoutRecyclerView = view.findViewById(R.id.workoutRecyclerView);

        // Set the Layout Manager
        workoutRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the WorkoutAdapter
        final WorkoutAdapter workoutAdapter = new WorkoutAdapter(getActivity(), this);
        workoutRecyclerView.setAdapter(workoutAdapter);

        workoutViewModel = new ViewModelProvider(this, new MyViewModelFactory(getActivity().getApplication())).get(WorkoutViewModel.class);
        workoutViewModel.getAllWorkouts().observe(getViewLifecycleOwner(), new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workouts) {
                // update RecyclerView
                workoutAdapter.setWorkouts(workouts);
            }
        });

        // Initialize FloatingActionButton and set its click listener
        fabInsertWorkout = view.findViewById(R.id.fab_insert_workout);
        fabInsertWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace this fragment with the CreateWorkoutFragment
                CreateWorkoutFragment createFragment = new CreateWorkoutFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, createFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onWorkoutClick(Workout workout) {
        // When a workout item is clicked, replace this fragment with a WorkoutDetailFragment
        Log.d("MainFragment", "Workout clicked: " + workout.getName());
        WorkoutDetailFragment detailFragment = WorkoutDetailFragment.newInstance(workout);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

