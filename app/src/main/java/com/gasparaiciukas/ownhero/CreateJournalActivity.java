package com.gasparaiciukas.ownhero;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateJournalActivity extends AppCompatActivity {

    private int journalId;
    private boolean edited = true;
    private EditText setJournalText;
    private int stepId;
    private FloatingActionButton fab;

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_journal);

        setJournalText = findViewById(R.id.text_journal_text);
        fab = findViewById(R.id.button_delete_journal);

        // Get IDs from intent
        journalId = getIntent().getIntExtra("journalId", -1);
        stepId = getIntent().getIntExtra("stepId", -3);

        // If the journal entry is created
        if (journalId == -1)
        {
            edited = false;
        }

        // If the journal entry is edited
        else {
            setJournalText.setHint(MainActivity.database.goalDao().journal_getById(journalId).text);
            setJournalText.setText(MainActivity.database.goalDao().journal_getById(journalId).text);
        }

        // Set up FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edited) {
                    MainActivity.database.goalDao().journal_delete(journalId);
                }
                finish();
            }
        });

        // Set up text editing (writing the journal entry)
        setJournalText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (setJournalText.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);

                    // When text is being edited, hide FAB
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

    // When the journal entry is submitted, text, current date and time are updated/saved to the database
    public void onSubmitJournalClicked(View view) {
        String journalText = setJournalText.getText().toString();
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        java.sql.Time currentTime = new java.sql.Time(System.currentTimeMillis());
        if (!edited) {
            MainActivity.database.goalDao().journal_create(stepId, journalText, currentDate, currentTime);
            finish();
        }
        else {
            MainActivity.database.goalDao().journal_updateText(journalId, journalText);
            MainActivity.database.goalDao().journal_updateDate(journalId, currentDate);
            MainActivity.database.goalDao().journal_updateTime(journalId, currentTime);
            finish();
        }
    }

    // On back button pressed, start showing FAB
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fab.show();
    }
}
