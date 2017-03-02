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

    public static final String LOG_TAG = SettingsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.i(LOG_TAG , "TEST: onCreate() Settings ") ;

    }

    public static class ArticlesPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        private SharedPreferences.OnSharedPreferenceChangeListener prefListener;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Log.i(LOG_TAG , "TEST: onCreate() Fragment ") ;

            final Preference category = findPreference(getString(R.string.settings_category_key));
            bindPreferenceSummaryToValue(category);

            //listener on changed sort order preference:
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());


        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            Log.i(LOG_TAG , "TEST: onPreferenceChange()") ;

            String stringValue = value.toString();
            preference.setSummary(stringValue);
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            Log.i(LOG_TAG , "TEST: bindPreferenceSummaryToValue()") ;
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}
