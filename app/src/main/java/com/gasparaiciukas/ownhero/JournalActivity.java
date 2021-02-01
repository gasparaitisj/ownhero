package com.gasparaiciukas.ownhero;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class JournalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private JournalAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private int stepId;
    private boolean motivation;
    private TextView stepTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        // Get variables from intent
        stepId = getIntent().getIntExtra("stepId", -3);
        motivation = getIntent().getBooleanExtra("motivation", false);
        stepTitle = findViewById(R.id.journal_title_text);
        stepTitle.setText(getIntent().getStringExtra("stepTitle").toLowerCase());

        // Set up recycler view
        recyclerView = findViewById(R.id.journal_recycler_view);
        adapter = new JournalAdapter(stepId);
        layoutManager = new LinearLayoutManager(this);

        // Swipe actions and FAB
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            // Action on move (necessary, but does not do anything as of now)
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // Swipe action (edit goal)
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), CreateJournalActivity.class);
                int journalId = viewHolder.getAdapterPosition();
                int id = JournalAdapter.journal.get(journalId).id;
                intent.putExtra("journalId", id);
                viewHolder.itemView.getContext().startActivity(intent);
            }

            // Put style on swipe action (yellow background while swiping)
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.colorAccent));
                View itemView = layoutManager.findViewByPosition(viewHolder.getAdapterPosition());
                assert itemView != null;
                background.setBounds(0, itemView.getTop(), (int) (itemView.getLeft() + dX), itemView.getBottom());
                background.draw(c);
            }
        };

        // Set up recycler view
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Attach swipe actions to recycler view
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.button_add_journal);

        // Set up FAB
        if (!motivation) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), CreateJournalActivity.class);
                    intent.putExtra("stepId", stepId);
                    v.getContext().startActivity(intent);
                }
            });
        }

        // If step is accessed from the motivation activity, hide the FAB
        else {
            fab.hide();
        }
    }

    // Reload adapter on resume
    @Override
    protected void onResume() {
        super.onResume();
        adapter.reload();
    }

    // Transfer view and status to editing the journal entry
    public void onJournalTextViewClicked(View view) {
        Intent intent = new Intent(view.getContext(), CreateJournalActivity.class);
        int journalId = Objects.requireNonNull(recyclerView.findContainingViewHolder(view)).getAdapterPosition();
        int id = JournalAdapter.journal.get(journalId).id;
        intent.putExtra("journalId", id);
        view.getContext().startActivity(intent);
    }
}
