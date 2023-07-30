package com.example.leachpeach.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leachpeach.R;
import com.example.leachpeach.model.Completion;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class CompletionAdapter extends RecyclerView.Adapter<CompletionAdapter.CompletionViewHolder> {
    private List<Completion> completions = new ArrayList<>();
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

    @NonNull
    @Override
    public CompletionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.completion_item, parent, false);
        return new CompletionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletionViewHolder holder, int position) {
        Completion currentCompletion = completions.get(position);
        holder.completionDate.setText(dateFormat.format(currentCompletion.getDate()));
        if (!currentCompletion.getWorkouts().isEmpty()) {
            holder.workoutName.setText(currentCompletion.getWorkouts().get(0));
        } else {
            holder.workoutName.setText("");
        }
    }


    @Override
    public int getItemCount() {
        return completions.size();
    }

    public void setCompletions(List<Completion> completions) {
        this.completions = completions;
        notifyDataSetChanged();
    }

    class CompletionViewHolder extends RecyclerView.ViewHolder {
        private TextView completionDate;
        private TextView workoutName;

        public CompletionViewHolder(@NonNull View itemView) {
            super(itemView);
            completionDate = itemView.findViewById(R.id.text_view_completion_date);
            workoutName = itemView.findViewById(R.id.text_view_workout_name);
        }
    }
}
