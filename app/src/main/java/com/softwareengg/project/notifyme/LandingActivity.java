package com.softwareengg.project.notifyme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.szugyi.circlemenu.view.CircleImageView;
import com.szugyi.circlemenu.view.CircleLayout;

/**
 * Created by startup on 21-04-2017.
 */

public class LandingActivity extends AppCompatActivity implements
        CircleLayout.OnItemClickListener,
        CircleLayout.OnCenterClickListener {

    protected CircleLayout circleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        // Set listeners
        circleLayout = (CircleLayout) findViewById(R.id.circle_layout);
        circleLayout.setOnItemClickListener(this);
        circleLayout.setOnCenterClickListener(this);

        String name = null;
        View view = circleLayout.getSelectedItem();
        if (view instanceof CircleImageView) {
            name = ((CircleImageView) view).getName();
        }

    }

    @Override
    public void onItemClick(View view) {
        String name = null;
        if (view instanceof CircleImageView) {
            name = ((CircleImageView) view).getName();
        }
        Intent mainIntent = new Intent(this, MainActivity.class);
        switch (view.getId()) {
            case R.id.main_food:
                mainIntent.putExtra("Category", 1);
                break;
            case R.id.main_travel:
                mainIntent.putExtra("Category", 2);
                break;
            case R.id.main_clothing:
                mainIntent.putExtra("Category", 3);
                break;
            case R.id.main_accessories:
                mainIntent.putExtra("Category", 4);
                break;
            case R.id.main_movies:
                mainIntent.putExtra("Category", 5);
                break;
            case R.id.main_misc:
                mainIntent.putExtra("Category", 6);
                break;
        }
        startActivity(mainIntent);
    }


    @Override
    public void onCenterClick() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.putExtra("Category", 0);
        startActivity(mainIntent);
    }


}