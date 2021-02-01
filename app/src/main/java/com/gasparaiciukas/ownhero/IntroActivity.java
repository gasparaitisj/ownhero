package com.gasparaiciukas.ownhero;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create slides for app intro
        // A slide contains: a title, a description, an image and a color
        // See more: https://github.com/AppIntro/AppIntro
        addSlide(AppIntroFragment.newInstance("welcome on board!", // title
                "enable yourself\nto\nbecome your own hero", // description
                R.drawable.lighthouse, // image
                getResources().getColor(R.color.colorAccent))); // color

        addSlide(AppIntroFragment.newInstance("become self-motivated",
                "you are\nwhat\nyou accomplish",
                R.drawable.motivation,
                getResources().getColor(R.color.colorAccent)));

        addSlide(AppIntroFragment.newInstance("track your progress",
                "break down your ambitions \n into \n goals & steps",
                R.drawable.goals,
                getResources().getColor(R.color.colorAccent)));

        addSlide(AppIntroFragment.newInstance("track your progress",
                "break down your ambitions \n into \n goals & steps",
                R.drawable.steps,
                getResources().getColor(R.color.colorAccent)));

        addSlide(AppIntroFragment.newInstance("write a journal",
                "put your thoughts & feelings \n into \n the journal of each step",
                R.drawable.journal,
                getResources().getColor(R.color.colorAccent)));

        addSlide(AppIntroFragment.newInstance("use your accomplishments as fuel for further achievement",
                "check a goal as done \n & \n bathe in the glory of your triumph",
                R.drawable.save_goal,
                getResources().getColor(R.color.colorAccent)));

        addSlide(AppIntroFragment.newInstance("become\n the best version of yourself",
                "the only real hero \n in this world \n is you",
                R.drawable.becomebetter,
                getResources().getColor(R.color.colorAccent)));

        showSkipButton(false);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
