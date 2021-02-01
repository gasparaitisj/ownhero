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

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder>{

    private int goalId;
    // Set up recycler view view holder
    public static class StepViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private CheckBox checkBox;
        private TextView dateView;
        private TextView timeView;

        StepViewHolder (View view) {
            super(view);
            textView = view.findViewById(R.id.step_row_text);
            checkBox = view.findViewById(R.id.step_row_checkbox);
            dateView = view.findViewById(R.id.step_row_date);
            timeView = view.findViewById(R.id.step_row_time);
        }

    }


    StepAdapter(int goal_id) {
        goalId = goal_id;
    }

    // Get step list
    static List<Step> steps = new ArrayList<>();

    public void reload() {
        steps = MainActivity.database.goalDao().step_getAllByGoalId(goalId);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_row, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        // Set goal status from database
        holder.textView.setText(steps.get(position).title);
        holder.checkBox.setChecked(steps.get(position).completed);
        holder.dateView.setText(steps.get(position).date.toString());
        holder.timeView.setText(steps.get(position).time.toString());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

}
