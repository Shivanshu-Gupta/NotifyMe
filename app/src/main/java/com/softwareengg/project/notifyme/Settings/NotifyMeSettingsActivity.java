package com.softwareengg.project.notifyme.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.softwareengg.project.notifyme.R;

/**
 * Created by startup on 25-04-2017.
 */

public class NotifyMeSettingsActivity extends PreferenceActivity  implements SharedPreferences.OnSharedPreferenceChangeListener{
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new NotifyMePreferenceFragment()).commit();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

}

