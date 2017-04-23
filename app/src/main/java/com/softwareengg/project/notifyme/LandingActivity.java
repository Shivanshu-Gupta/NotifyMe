package com.softwareengg.project.notifyme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.szugyi.circlemenu.view.CircleImageView;
import com.szugyi.circlemenu.view.CircleLayout;

/**
 * Created by startup on 21-04-2017.
 */

public class LandingActivity extends AppCompatActivity implements CircleLayout.OnItemSelectedListener,
        CircleLayout.OnItemClickListener, CircleLayout.OnCenterClickListener {
    public static final String ARG_LAYOUT = "layout";

    protected CircleLayout circleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);

        // Set listeners
        circleLayout = (CircleLayout) findViewById(R.id.circle_layout);
        circleLayout.setOnItemSelectedListener(this);
        circleLayout.setOnItemClickListener(this);
//        circleLayout.setOnRotationFinishedListener(this);
        circleLayout.setOnCenterClickListener(this);

        String name = null;
        View view = circleLayout.getSelectedItem();
        if (view instanceof CircleImageView) {
            name = ((CircleImageView) view).getName();
        }

    }
    @Override
    public void onItemSelected(View view) {
        final String name;
        if (view instanceof CircleImageView) {
            name = ((CircleImageView) view).getName();
        } else {
            name = null;
        }

        switch (view.getId()) {
            case R.id.main_cab:
                // Handle calendar selection
                //TODO:fill these cases depending upon the category
                break;
            case R.id.main_stay:
                // Handle cloud selection
                break;
            case R.id.main_menu:
                // Handle key selection
                break;
            case R.id.main_food:
                // Handle mail selection
                break;
            case R.id.main_movies:
                // Handle profile selection
                break;
            case R.id.main_misc:
                // Handle tap selection
                break;
        }
    }

    @Override
    public void onItemClick(View view) {
        String name = null;
        if (view instanceof CircleImageView) {
            name = ((CircleImageView) view).getName();
        }

        switch (view.getId()) {
            case R.id.main_cab:
                // Handle calendar selection
                //TODO:fill these cases depending upon the category
                break;
            case R.id.main_stay:
                // Handle cloud selection
                break;
            case R.id.main_menu:
                // Handle key selection
                break;
            case R.id.main_food:
                // Handle mail selection
                break;
            case R.id.main_movies:
                // Handle profile selection
                break;
            case R.id.main_misc:
                // Handle tap selection
                break;
        }

        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }


    @Override
    public void onCenterClick() {
        Toast.makeText(getApplicationContext(), R.string.center_click, Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(this, MainActivity.class);
    }


}