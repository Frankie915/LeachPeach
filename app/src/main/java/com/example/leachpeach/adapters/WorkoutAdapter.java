package com.example.leachpeach.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.R;
import com.example.leachpeach.fragments.WorkoutDetailFragment;
import com.example.leachpeach.model.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private List<Workout> workouts = new ArrayList<>();

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        return new WorkoutViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout currentWorkout = workouts.get(position);
        holder.workoutName.setText(currentWorkout.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkoutDetailFragment workoutDetailFragment = new WorkoutDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("workoutId", currentWorkout.getId());
                workoutDetailFragment.setArguments(bundle);
                FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, workoutDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
        notifyDataSetChanged();
    }

    class WorkoutViewHolder extends RecyclerView.ViewHolder {
        private TextView workoutName;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.text_view_workout_name);
        }
    }
}
