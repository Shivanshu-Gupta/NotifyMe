package com.softwareengg.project.notifyme.Settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.softwareengg.project.notifyme.R;

/**
 * Created by startup on 25-04-2017.
 * This is the settings UI. The user can toggle notifications on/off and can indicate vendors that
 * he/she is interested in.
 */
public class NotifyMeSettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // show the Fragment containing preferences UI.
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
            // set default values
            PreferenceManager.setDefaultValues(getActivity(), R.xml.notifyme_setting, true);
        }
    }
}

