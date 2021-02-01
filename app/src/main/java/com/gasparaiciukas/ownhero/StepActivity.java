package com.gasparaiciukas.ownhero;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class StepActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StepAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private int goalId;
    private boolean motivation;
    private TextView goalTitle;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        // Get variables from intent
        goalId = getIntent().getIntExtra("goalId", -3); // get goal id from intent
        motivation = getIntent().getBooleanExtra("motivation", false); // to know if step is accessed from the motivation activity

        goalTitle = findViewById(R.id.step_title_text);
        goalTitle.setText(getIntent().getStringExtra("goalTitle").toLowerCase()); // set goal title

        // Set up recycler view
        recyclerView = findViewById(R.id.step_recycler_view);
        adapter = new StepAdapter(goalId);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.button_add_step);

        // Swipe actions and FAB
        if (!motivation) {
            ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

                // Action on move (necessary, but does not do anything as of now)
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                // Swipe action (edit goal)
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    Intent intent = new Intent(viewHolder.itemView.getContext(), CreateStepActivity.class);
                    int stepId = viewHolder.getAdapterPosition();
                    int id = StepAdapter.steps.get(stepId).id;
                    intent.putExtra("id", id);
                    adapter.reload();
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

            // Attach swipe actions to recycler view
            new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

            // Set up FAB
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), CreateStepActivity.class);
                    intent.putExtra("goalId", goalId);
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

    // Update checkbox status on click
    public void onStepCheckBoxClicked(View view) {
        if (!motivation) {
            int stepId = Objects.requireNonNull(recyclerView.findContainingViewHolder(view)).getAdapterPosition();
            boolean status = ((CheckBox) view).isChecked();
            int id = StepAdapter.steps.get(stepId).id;
            MainActivity.database.goalDao().step_updateStatus(id, status);
        }
    }
    // Transfer status and view to journal activity on step click
    public void onStepTextViewClicked(View view) {
        Intent intent = new Intent(view.getContext(), JournalActivity.class);
        int stepId = Objects.requireNonNull(recyclerView.findContainingViewHolder(view)).getAdapterPosition();
        int id = StepAdapter.steps.get(stepId).id;
        intent.putExtra("stepId", id);
        intent.putExtra("stepTitle", StepAdapter.steps.get(stepId).title);
        if (motivation) {
            intent.putExtra("motivation", true);
        }
        view.getContext().startActivity(intent);
    }
}
