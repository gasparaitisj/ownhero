package com.gasparaiciukas.ownhero;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder>{

    private int stepId;
    // Set up recycler view view holder
    public static class JournalViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView dateView;
        private TextView timeView;

        JournalViewHolder (View view) {
            super(view);
            textView = view.findViewById(R.id.journal_row_text);
            dateView = view.findViewById(R.id.journal_row_date);
            timeView = view.findViewById(R.id.journal_row_time);
        }

    }


    JournalAdapter(int step_id) {
        stepId = step_id;
    }

    // Get journal entry list
    static List<JournalEntry> journal = new ArrayList<>();

    public void reload() {
        journal = MainActivity.database.goalDao().journal_getAllByStepId(stepId);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_row, parent, false);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        // Set goal status from database
        holder.textView.setText(journal.get(position).text);
        holder.dateView.setText(journal.get(position).date.toString());
        holder.timeView.setText(journal.get(position).time.toString());
    }

    @Override
    public int getItemCount() {
        return journal.size();
    }

}
