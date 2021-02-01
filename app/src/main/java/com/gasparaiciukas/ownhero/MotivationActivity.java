package com.gasparaiciukas.ownhero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class MotivationActivity extends AppCompatActivity {

    // Set up recycler view
    private RecyclerView recyclerView;
    private MotivationAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation);

        // Set up recycler view
        recyclerView = findViewById(R.id.motivation_recycler_view);
        adapter = new MotivationAdapter();
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    // Reload adapter on resume
    @Override
    protected void onResume() {
        super.onResume();
        adapter.reload();
    }

    // On goal clicked, transfer view to step view
    public void onMotivationTextViewClicked(View view) {
        Intent intent = new Intent(view.getContext(), StepActivity.class);
        int goalId = Objects.requireNonNull(recyclerView.findContainingViewHolder(view)).getAdapterPosition();
        int id = MotivationAdapter.goalsCompleted.get(goalId).id;

        // Put data to intent
        intent.putExtra("goalId", id);
        intent.putExtra("goalTitle", MotivationAdapter.goalsCompleted.get(goalId).title);
        intent.putExtra("motivation", true); // to know that access comes from the motivation activity
        view.getContext().startActivity(intent);
    }


    public void onMotivationCheckBoxClicked(final View view) {
        // Set up checkbox animation
        final Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setDuration(500);

        // Action when box checked
        int goalId = Objects.requireNonNull(recyclerView.findContainingViewHolder(view)).getAdapterPosition(); // get goal number from adapter
        final boolean status = ((CheckBox) view).isChecked(); // get goal status (checked or not)
        final int id = MotivationAdapter.goalsCompleted.get(goalId).id; // get goal id by number
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