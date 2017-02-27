package com.example.android.newsapp;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static android.R.attr.value;
import static com.example.android.newsapp.NewsActivity.ARTICLE_LOADER_ID;

public class SettingsActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    public static class ArticlesPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        private SharedPreferences.OnSharedPreferenceChangeListener prefListener;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            final Preference category = findPreference(getString(R.string.settings_category_key));
            bindPreferenceSummaryToValue(category);

            //listener on changed sort order preference:
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

            prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                    /*This section is suppose to implement the restartLoader() method when the key :
                    category changes and it will restart the Loader which is in the NewsActivity.
                     Source : http://envyandroid.com/android-detect-preference-changes/
                    */

                    Log.i("PreferenceFragment", "Settings key changed: " + category);
                    if(key.equals(category))
                        getLoaderManager().restartLoader(ARTICLE_LOADER_ID, null, NewsActivity);

                }
            };

            prefs.registerOnSharedPreferenceChangeListener(prefListener);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            preference.setSummary(stringValue);
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}
