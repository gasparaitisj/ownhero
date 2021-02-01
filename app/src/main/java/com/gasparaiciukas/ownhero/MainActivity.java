package com.gasparaiciukas.ownhero;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {

    // Set database
    public static GoalsDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start app intro, if app launched for the first time

        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    final Intent i = new Intent(MainActivity.this, IntroActivity.class);

                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            startActivity(i);
                        }
                    });

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();

        setContentView(R.layout.activity_main);

        // Build database
        database = Room
                .databaseBuilder(getApplicationContext(), GoalsDatabase.class, "goals")
                .allowMainThreadQueries()
                .build();
    }

    // When progress button is clicked, transfer view to the progress screen
    public void onProgressButtonClick(View view) {
        Intent intent = new Intent(view.getContext(), ProgressActivity.class);
        view.getContext().startActivity(intent);
    }

    // When motivation button is clicked, transfer view to the motivation screen
    public void onMotivationButtonClick(View view) {
        Intent intent = new Intent(view.getContext(), MotivationActivity.class);
        view.getContext().startActivity(intent);
    }

    // When information button is clicked, transfer view to the app intro
    public void onInformationButtonClick(View view) {
        Intent intent = new Intent(view.getContext(), IntroActivity.class);
        view.getContext().startActivity(intent);
    }
}
