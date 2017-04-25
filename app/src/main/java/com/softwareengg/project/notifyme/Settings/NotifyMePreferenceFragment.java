package com.softwareengg.project.notifyme.Settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.softwareengg.project.notifyme.R;

/**
 * Created by startup on 26-04-2017.
 */

public class NotifyMePreferenceFragment extends PreferenceFragment
{
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.notifyme_setting);
    }
}