package com.gasparaiciukas.ownhero;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class ProgressActivity extends AppCompatActivity {

    // Set up recycler view
    private RecyclerView recyclerView;
    private ProgressAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        // Set up recycler view
        recyclerView = findViewById(R.id.progress_recycler_view);
        adapter = new ProgressAdapter();
        layoutManager = new LinearLayoutManager(this);

        // Item swipe actions
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            // Action on move (necessary, but does not do anything as of now)
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // Swipe action (edit goal)
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), CreateGoalActivity.class);
                int goalId = viewHolder.getAdapterPosition();
                int id = ProgressAdapter.goals.get(goalId).id;
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
                background.setBounds(0, itemView.getTop(), (int) (itemView.getLeft() + dX), itemView.getBottom());
                background.draw(c);
            }
        };

        // Set up recycler view
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Attach swipe actions to recycler view items
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);


        // Add goal button
        FloatingActionButton fab = findViewById(R.id.button_add_goal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateGoalActivity.class);
                int goalId = -1; // for distinguishing if goal is edited (-1 = not edited)
                intent.putExtra("id", goalId);
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.reload(); // reloads adapter (updates uncompleted goals)
    }

    // On goal clicked, transfer view to step view
    public void onProgressTextViewClicked(View view) {
        Intent intent = new Intent(view.getContext(), StepActivity.class);
        int goalId = Objects.requireNonNull(recyclerView.findContainingViewHolder(view)).getAdapterPosition(); // get goal number from adapter
        int id = ProgressAdapter.goals.get(goalId).id; // get goal id by number
        intent.putExtra("goalId", id); // put goal id to access later
        intent.putExtra("goalTitle", ProgressAdapter.goals.get(goalId).title); // put goal title to access later
        view.getContext().startActivity(intent); // start step activity
    }


    public void onProgressCheckBoxClicked(final View view) {
        // Set up checkbox animation
        final Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setDuration(500);

        // Action when box checked
        final int goalId = Objects.requireNonNull(recyclerView.findContainingViewHolder(view)).getAdapterPosition(); // get goal number from adapter
        final boolean status = ((CheckBox) view).isChecked(); // get goal status (checked or not)
        final int id = ProgressAdapter.goals.get(goalId).id; // get goal id by number
        Objects.requireNonNull(recyclerView.findContainingViewHolder(view)).itemView.startAnimation(fadeOut); // start animation
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
                view.setClickable(false); // prevent user from completing a goal more than once
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                view.setClickable(true); // reset preventing
                MainActivity.database.goalDao().goal_updateStatus(id, status); // update database
                adapter.reload();
            }
        });
    }
}