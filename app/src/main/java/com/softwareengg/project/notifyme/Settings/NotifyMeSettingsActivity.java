package com.softwareengg.project.notifyme.Settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.softwareengg.project.notifyme.R;

/**
 * Created by startup on 25-04-2017.
 */

public class NotifyMeSettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new NotifyMePreferenceFragment()).commit();
    }

    public static class NotifyMePreferenceFragment extends PreferenceFragment
    {
        private static final String TAG = "NotifyMe";
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.notifyme_setting);
            PreferenceManager.setDefaultValues(getActivity(), R.xml.notifyme_setting, true);
        }
    }

}

