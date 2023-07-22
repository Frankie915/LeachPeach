package com.example.leachpeach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.model.Exercise;

import java.util.ArrayList;

public class EditExerciseAdapter extends RecyclerView.Adapter<EditExerciseAdapter.EditExerciseViewHolder> {

    private ArrayList<Exercise> exercises;

    public EditExerciseAdapter(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public EditExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_exercise_item, parent, false);
        return new EditExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditExerciseViewHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);
        holder.bind(currentExercise);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public Exercise getExerciseAt(int position) {
        return exercises.get(position);
    }

    class EditExerciseViewHolder extends RecyclerView.ViewHolder {

        EditText exerciseNameEditText;

        public EditExerciseViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseNameEditText = itemView.findViewById(R.id.exerciseNameEditText);
        }

        void bind(final Exercise exercise) {
            exerciseNameEditText.setText(exercise.getName());

            // Find the EditTexts for weight, reps, and sets and set their values
            EditText exerciseWeightEditText = itemView.findViewById(R.id.weightEditText);
            EditText exerciseRepsEditText = itemView.findViewById(R.id.repsEditText);
            EditText exerciseSetsEditText = itemView.findViewById(R.id.setsEditText);


            exerciseWeightEditText.setText(String.valueOf(exercise.getWeight()));
            exerciseRepsEditText.setText(String.valueOf(exercise.getReps()));
            exerciseSetsEditText.setText(String.valueOf(exercise.getSets()));
        }

    }
}

