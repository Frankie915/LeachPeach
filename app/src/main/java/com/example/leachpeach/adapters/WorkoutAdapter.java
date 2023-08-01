package com.example.leachpeach.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.R;
import com.example.leachpeach.fragments.WorkoutDetailFragment;
import com.example.leachpeach.model.Workout;
import com.example.leachpeach.fragments.WorkoutFragment;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

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
                Bundle bundle = new Bundle();
                bundle.putInt("workoutId", currentWorkout.getId());

                // Use Navigation Component to navigate to WorkoutDetailFragment
                NavController navController = Navigation.findNavController((Activity) v.getContext(), R.id.fragment_container);
                navController.navigate(R.id.action_mainFragment_to_workoutDetailFragment, bundle);
            }
        });


    }

    public void setSortOrder(WorkoutFragment.WorkoutSortOrder sortOrder) {
        switch (sortOrder) {
            case ALPHABETIC:
                workouts.sort(Comparator.comparing(Workout::getName));
                break;
            case NEW_FIRST:
                workouts.sort(Comparator.comparing(Workout::getUpdateTime).reversed());
                break;
            default:
                workouts.sort(Comparator.comparing(Workout::getUpdateTime));
        }
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
