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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.R;
import com.example.leachpeach.adapters.ExerciseViewAdapter;
import com.example.leachpeach.model.Exercise;
import com.example.leachpeach.viewmodel.ExerciseViewModel;
import com.example.leachpeach.viewmodel.MyViewModelFactory;

import java.util.List;


public class ExerciseFragment extends Fragment {

    private ExerciseViewModel exerciseViewModel;
    private ExerciseViewAdapter exerciseViewAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_exercises);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        exerciseViewModel = new ViewModelProvider(requireActivity(), new MyViewModelFactory(requireActivity().getApplication())).get(ExerciseViewModel.class);
        exerciseViewAdapter = new ExerciseViewAdapter();
        recyclerView.setAdapter(exerciseViewAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        exerciseViewModel.getAllExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                exerciseViewAdapter.setExercises(exercises);
            }
        });
    }
}

