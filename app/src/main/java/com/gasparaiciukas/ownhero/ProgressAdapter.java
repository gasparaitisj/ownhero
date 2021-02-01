package com.gasparaiciukas.ownhero;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder> {

    // Set up recycler view view holder
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout containerView;
        private TextView textView;
        private CheckBox checkBox;
        private TextView dateView;
        private TextView timeView;

        ProgressViewHolder (View view) {
            super(view);
            containerView = view.findViewById(R.id.progress_row);
            textView = view.findViewById(R.id.progress_row_text);
            checkBox = view.findViewById(R.id.progress_row_checkbox);
            dateView = view.findViewById(R.id.progress_row_date);
            timeView = view.findViewById(R.id.progress_row_time);
        }

    }

    // Get goal list
    static List<Goal> goals = new ArrayList<>();

    public void reload() {
        goals = MainActivity.database.goalDao().goal_getByCompletion(false); // get only uncompleted goals
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_row, parent, false);
        return new ProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressViewHolder holder, int position) {
        // Set goal status from database
        holder.textView.setText(goals.get(position).title);
        holder.checkBox.setChecked(goals.get(position).completed);
        holder.dateView.setText(goals.get(position).date.toString());
        holder.timeView.setText(goals.get(position).time.toString());
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

}
