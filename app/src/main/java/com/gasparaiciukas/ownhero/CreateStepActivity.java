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

public class CreateStepActivity extends AppCompatActivity {

    private int stepId;
    private boolean edited = true;
    private TextView stepCreate;
    private EditText setStepTitle;
    private int goalId;

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_step);

        stepCreate = findViewById(R.id.text_step_create);
        setStepTitle = findViewById(R.id.text_step_title);

        // Get IDs from intent
        stepId = getIntent().getIntExtra("id", -1);
        goalId = getIntent().getIntExtra("goalId", -3);

        // If the step is created
        if (stepId == -1)
        {
            edited = false;
            stepCreate.setText("Set a step title...");
        }

        // If the step is edited
        else {
            setStepTitle.setHint(MainActivity.database.goalDao().step_getById(stepId).title);
            setStepTitle.setText(MainActivity.database.goalDao().step_getById(stepId).title);
        }

        // Set up FAB
        final FloatingActionButton fab = findViewById(R.id.button_delete_step);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edited) {
                    MainActivity.database.goalDao().step_delete(stepId);
                }
                finish();
            }
        });

        // Set up text editing (writing the title of the step)
        setStepTitle.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (setStepTitle.hasFocus()) {
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


    // When the step is submitted, title, current date and time are updated/saved to the database
    public void onSubmitStepClicked(View view) {
        String stepTitle = setStepTitle.getText().toString();
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        java.sql.Time currentTime = new java.sql.Time(System.currentTimeMillis());
        if (!edited) {
            MainActivity.database.goalDao().step_create(stepTitle, currentDate, currentTime, goalId);
            finish();
        }
        else {
            MainActivity.database.goalDao().step_updateTitle(stepId, stepTitle);
            finish();
        }
    }
}
