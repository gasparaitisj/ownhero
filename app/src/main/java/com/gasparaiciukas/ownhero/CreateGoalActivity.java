package com.gasparaiciukas.ownhero;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateGoalActivity extends AppCompatActivity {

    private int goalId;
    private boolean edited = true;
    private TextView goalCreate;
    private EditText setGoalTitle;

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);
        goalCreate = findViewById(R.id.text_goal_create);
        setGoalTitle = findViewById(R.id.text_goal_title);
        goalId = getIntent().getIntExtra("id", -2); // get goal id

        // If the goal is created
        if (goalId == -1)
        {
            edited = false;
            goalCreate.setText("Set a goal title...");
        }

        // If the goal is edited
        else {
            setGoalTitle.setHint(MainActivity.database.goalDao().goal_getById(goalId).title);
            setGoalTitle.setText(MainActivity.database.goalDao().goal_getById(goalId).title);
        }

        // Create FAB
        final FloatingActionButton fab = findViewById(R.id.button_delete_goal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edited) {
                    MainActivity.database.goalDao().goal_delete(goalId);
                    MainActivity.database.goalDao().step_deleteAllByGoalId(goalId);
                }
                finish();
            }
        });

        // Set up text editing (writing the title of the goal)
        setGoalTitle.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (setGoalTitle.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    fab.hide();
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });
    }

    // When the goal is submitted, title, current date and time are updated/saved to the database
    public void onSubmitGoalClicked(View view) {
        String goalTitle = setGoalTitle.getText().toString();
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        java.sql.Time currentTime = new java.sql.Time(System.currentTimeMillis());
        if (!edited) {
            MainActivity.database.goalDao().goal_create(goalTitle, currentDate, currentTime);
        }
        else {
            MainActivity.database.goalDao().goal_updateTitle(goalId, goalTitle);
        }
        finish();
    }
}
