package com.softwareengg.project.notifyme.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.softwareengg.project.notifyme.R;

/**
 * Created by startup on 26-04-2017.
 */
//settings for user preferences
public class NotifyMePreferenceFragment extends PreferenceFragment
{
    SharedPreferences sharedPref;
    private static final String TAG = "NotifyMe";
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.notifyme_setting);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.notifyme_setting, true);

    }
}