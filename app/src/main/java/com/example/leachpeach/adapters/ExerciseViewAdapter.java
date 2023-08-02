package com.example.leachpeach.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_view_item, parent, false);
        return new ExerciseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);
        holder.textViewName.setText(currentExercise.getName());
        holder.textViewWeight.setText(String.valueOf(currentExercise.getWeight()));
        holder.textViewSets.setText(String.valueOf(currentExercise.getSets()));
        holder.textViewReps.setText(String.valueOf(currentExercise.getReps()));
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
        public TextView textViewName;
        public TextView textViewWeight;
        public TextView textViewSets;
        public TextView textViewReps;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.exercise_name);
            textViewWeight = itemView.findViewById(R.id.exercise_weight);
            textViewSets = itemView.findViewById(R.id.exercise_sets);
            textViewReps = itemView.findViewById(R.id.exercise_reps);
        }
    }
}
