package com.example.leachpeach.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.R;
import com.example.leachpeach.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseViewAdapter extends RecyclerView.Adapter<ExerciseViewAdapter.ExerciseViewHolder> {

    private List<Exercise> exercises = new ArrayList<>();

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_detail_item, parent, false);
        return new ExerciseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);
        holder.editTextName.setText(currentExercise.getName());
        holder.editTextWeight.setText(String.valueOf(currentExercise.getWeight()));
        holder.editTextSets.setText(String.valueOf(currentExercise.getSets()));
        holder.editTextReps.setText(String.valueOf(currentExercise.getReps()));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public EditText editTextName;
        public EditText editTextWeight;
        public EditText editTextSets;
        public EditText editTextReps;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            editTextName = itemView.findViewById(R.id.exercise_name);
            editTextWeight = itemView.findViewById(R.id.exercise_weight);
            editTextSets = itemView.findViewById(R.id.exercise_sets);
            editTextReps = itemView.findViewById(R.id.exercise_reps);
        }
    }
}
