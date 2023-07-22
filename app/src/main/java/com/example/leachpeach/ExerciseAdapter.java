package com.example.leachpeach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.model.Exercise;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private ArrayList<Exercise> exercises;

    public ExerciseAdapter() {
        this.exercises = new ArrayList<>();
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);
        holder.bind(currentExercise);
    }

    @Override
    public int getItemCount() {
        if (exercises == null) {
            return 0;
        } else {
            return exercises.size();
        }
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        notifyItemInserted(exercises.size() - 1);
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseNameTextView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseNameTextView = itemView.findViewById(R.id.exerciseNameTextView);
        }

        void bind(final Exercise exercise) {
            String exerciseDetails = String.format("%s: %d lbs, %d sets, %d reps",
                    exercise.getName(), exercise.getWeight(), exercise.getSets(), exercise.getReps());
            exerciseNameTextView.setText(exerciseDetails);
        }

    }
}

