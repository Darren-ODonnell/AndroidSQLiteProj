package com.example.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskResultsHolder> {
    private List<Task> results = new ArrayList<>();

    @NonNull
    @Override
    public TaskResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);

        return new TaskResultsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskResultsHolder holder, int position) {
        Task task = results.get(position);

        holder.categoryTV.setText(task.getCategory());
        holder.statusTV.setText(task.getStatus());
        holder.deadlineTV.setText(task.getDeadlineAsString());
        holder.descriptionTV.setText(task.getDescription());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Task> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    class TaskResultsHolder extends RecyclerView.ViewHolder {
        private TextView categoryTV;
        private TextView descriptionTV;
        private TextView deadlineTV;
        private TextView statusTV;

        public TaskResultsHolder(@NonNull View itemView) {
            super(itemView);

            categoryTV = itemView.findViewById(R.id.categoryTR);
            descriptionTV = itemView.findViewById(R.id.descriptionTR);
            deadlineTV = itemView.findViewById(R.id.deadlineTR);
            statusTV = itemView.findViewById(R.id.statusTR);
        }
    }
}
