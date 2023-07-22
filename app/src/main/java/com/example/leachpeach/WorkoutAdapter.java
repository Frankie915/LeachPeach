package com.example.leachpeach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.model.Workout;
import com.example.leachpeach.repository.WorkoutRepository;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private OnWorkoutClickListener listener;
    private FragmentActivity activity;

    public interface OnWorkoutClickListener {
        void onWorkoutClick(Workout workout);
    }

    // List of workouts to display
    private List<Workout> workouts = new ArrayList<>();

    // Constructor
    public WorkoutAdapter(FragmentActivity activity, OnWorkoutClickListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    // This method creates new ViewHolder objects whenever the RecyclerView needs a new one
    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        return new WorkoutViewHolder(view);
    }

    // This method gets called by the RecyclerView to display data at a specified position in the Cursor
    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        if (workouts != null && !workouts.isEmpty()) {
            final Workout currentWorkout = workouts.get(position);
            holder.bind(currentWorkout);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onWorkoutClick(currentWorkout);
                }
            });
        }
    }

    // This method tells the RecyclerView how many items to display
    @Override
    public int getItemCount() {
        if (workouts != null)
            return workouts.size();
        else
            return 0;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
        notifyDataSetChanged();
    }

    // ViewHolder class
    class WorkoutViewHolder extends RecyclerView.ViewHolder {

        // UI elements
        TextView workoutNameTextView;
        Button editWorkoutButton;
        Button deleteWorkoutButton;

        // Constructor
        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the UI elements
            workoutNameTextView = itemView.findViewById(R.id.workoutNameTextView);
            editWorkoutButton = itemView.findViewById(R.id.editWorkoutButton);
            deleteWorkoutButton = itemView.findViewById(R.id.deleteWorkoutButton);
        }

        void bind(final Workout workout) {
            // Update UI elements with data
            workoutNameTextView.setText(workout.getName());
            editWorkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Replace the current fragment with an EditWorkoutFragment
                    EditWorkoutFragment editFragment = EditWorkoutFragment.newInstance(workout);
                    FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, editFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
    }
}
