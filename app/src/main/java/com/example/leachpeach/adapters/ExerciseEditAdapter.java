package com.example.leachpeach.adapters;

import android.text.Editable;
import android.text.TextWatcher;
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

public class ExerciseEditAdapter extends RecyclerView.Adapter<ExerciseEditAdapter.ExerciseEditTextHolder> {
    private List<Exercise> exercises = new ArrayList<>();

    @NonNull
    @Override
    public ExerciseEditTextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item, parent, false);
        return new ExerciseEditTextHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseEditTextHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);
        holder.editTextName.setText(currentExercise.getName());
        holder.editTextWeight.setText(String.valueOf(currentExercise.getWeight()));
        holder.editTextReps.setText(String.valueOf(currentExercise.getReps()));
        holder.editTextSets.setText(String.valueOf(currentExercise.getSets()));

        holder.editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                currentExercise.setName(s.toString());
            }
        });

        holder.editTextWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    currentExercise.setWeight((int) Float.parseFloat(s.toString()));
                }
            }
        });

        holder.editTextReps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    currentExercise.setReps(Integer.parseInt(s.toString()));
                }
            }
        });

        holder.editTextSets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    currentExercise.setSets(Integer.parseInt(s.toString()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public class ExerciseEditTextHolder extends RecyclerView.ViewHolder {
        private EditText editTextName;
        private EditText editTextWeight;
        private EditText editTextReps;
        private EditText editTextSets;

        public ExerciseEditTextHolder(View itemView) {
            super(itemView);
            editTextName = itemView.findViewById(R.id.edit_text_exercise_name);
            editTextWeight = itemView.findViewById(R.id.edit_text_weight);
            editTextReps = itemView.findViewById(R.id.edit_text_reps);
            editTextSets = itemView.findViewById(R.id.edit_text_sets);
        }
    }
}
