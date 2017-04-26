package com.softwareengg.project.notifyme.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;

import com.softwareengg.project.notifyme.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by startup on 26-04-2017.
 */

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

        final MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) findPreference("vendors");
        multiSelectListPreference.setOnPreferenceChangeListener(new MultiSelectListPreference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                //if(!((Boolean) newValue)) {
                    //TODO: enable notifications  in shared prefs
                CharSequence[] entries = multiSelectListPreference.getEntries();
                CharSequence[] entryValues = multiSelectListPreference.getEntryValues();
                List<String> currentEntries = new ArrayList<>();
                Set<String> currentEntryValues = multiSelectListPreference.getValues();
                Boolean[] checkedStatus = new Boolean[entries.length];
                for (int i = 0; i < entries.length; i++) {
                    if (currentEntryValues.contains(entryValues[i])) {
                        currentEntries.add(entries[i].toString());
                        checkedStatus[i] = sharedPref.getBoolean(Integer.toString(i), true);
                    }
                }
                for(int i=0;i<currentEntries.size();i++){
                    Log.v(TAG, currentEntries.get(i).toString());
                }
                
                return true;
            }
        });
    }

}