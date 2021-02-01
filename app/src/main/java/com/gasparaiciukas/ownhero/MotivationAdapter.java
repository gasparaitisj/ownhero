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

public class MotivationAdapter extends RecyclerView.Adapter<MotivationAdapter.MotivationViewHolder> {

    // Set up recycler view view holder
    public static class MotivationViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout containerView;
        private TextView textView;
        private CheckBox checkBox;
        private TextView dateView;
        private TextView timeView;

        MotivationViewHolder (View view) {
            super(view);
            containerView = view.findViewById(R.id.motivation_row);
            textView = view.findViewById(R.id.motivation_row_text);
            checkBox = view.findViewById(R.id.motivation_row_checkbox);
            dateView = view.findViewById(R.id.motivation_row_date);
            timeView = view.findViewById(R.id.motivation_row_time);
        }

    }

    // Get completed goal list
    static List<Goal> goalsCompleted = new ArrayList<>();

    public void reload() {
        goalsCompleted = MainActivity.database.goalDao().goal_getByCompletion(true);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MotivationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.motivation_row, parent, false);
        return new MotivationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MotivationViewHolder holder, int position) {
        // Set goal status from database
        holder.textView.setText(goalsCompleted.get(position).title);
        holder.checkBox.setChecked(goalsCompleted.get(position).completed);
        holder.dateView.setText(goalsCompleted.get(position).date.toString());
        holder.timeView.setText(goalsCompleted.get(position).time.toString());
    }

    @Override
    public int getItemCount() {
        return goalsCompleted.size();
    }

}
