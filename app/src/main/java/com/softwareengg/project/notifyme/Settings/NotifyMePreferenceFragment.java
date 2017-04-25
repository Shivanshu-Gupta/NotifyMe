package com.softwareengg.project.notifyme.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

import com.softwareengg.project.notifyme.R;

/**
 * Created by startup on 26-04-2017.
 */

public class NotifyMePreferenceFragment extends PreferenceFragment
{
    SharedPreferences sharedPref;
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.notifyme_setting);

        //TODO:set prefs
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        SwitchPreference enableNotification = (SwitchPreference) findPreference("notify");
        enableNotification.setOnPreferenceChangeListener(new SwitchPreference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.notify), (Boolean) newValue);
                editor.commit();
                return true;
            }
        });

        MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) findPreference("vendors");
        multiSelectListPreference.setOnPreferenceChangeListener(new MultiSelectListPreference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                //if(!((Boolean) newValue)) {
                    //TODO: enable notifications  in shared prefs

                //} else {
                    //TODO: disable notifications in shared prefs
                //}
                return true;
            }
        });
    }

}