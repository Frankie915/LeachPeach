package com.example.leachpeach.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.viewmodel.MyViewModelFactory;
import com.example.leachpeach.R;
import com.example.leachpeach.adapters.WorkoutAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.leachpeach.model.Workout;
import com.example.leachpeach.viewmodel.WorkoutViewModel;

import java.util.List;

public class WorkoutFragment extends Fragment {

    private WorkoutViewModel workoutViewModel;
    private WorkoutAdapter workoutAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel and Adapter
        workoutViewModel = new ViewModelProvider(requireActivity(), new MyViewModelFactory(requireActivity().getApplication())).get(WorkoutViewModel.class);
        workoutAdapter = new WorkoutAdapter();

        // Setup RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(workoutAdapter);

        // Observe workouts LiveData
        workoutViewModel.getAllWorkouts().observe(getViewLifecycleOwner(), new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workouts) {
                workoutAdapter.setWorkouts(workouts);
            }
        });

        // Handle FloatingActionButton click
        FloatingActionButton addWorkoutButton = view.findViewById(R.id.add_workout_button);
        addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = NavHostFragment.findNavController(WorkoutFragment.this);
                navController.navigate(R.id.action_mainFragment_to_createWorkoutFragment);
            }
        });

    }
}
