package fr.nicolabo.myyoutube.view;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import fr.nicolabo.myyoutube.R;
import fr.nicolabo.myyoutube.controller.AppPreferencesTool;

/**
 * Created by nicolasboueme on 16/05/2016.
 */
public class SettingsFragment extends PreferenceFragment {

    private ListPreference listPreference;
    private AppPreferencesTool appPreferencesTool;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        appPreferencesTool = new AppPreferencesTool(getActivity());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listPreference = (ListPreference) getPreferenceScreen().findPreference("PREF_THEME");

        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int index = listPreference.findIndexOfValue(newValue.toString());
                if (index != -1) {
                    Toast.makeText(getActivity(), listPreference.getEntries()[index], Toast.LENGTH_SHORT).show();

                    listPreference.getSharedPreferences().getString("", "");
                    //sharedPrefs.edit.putString("theme", "dark");
                    //getActivity().setTheme(R.style.MyYouTubeTheme_Dark);
                    //theme.applyStyle(R.style.MyYouTubeTheme_Dark, true);
                    getActivity().recreate();
                }
                return true;
            }
        });

        Preference logOut = findPreference("PREF_LOG_OUT");
        logOut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                appPreferencesTool.removeAllPrefs();
                Intent intent = new Intent(getActivity(), ConnectionActivity.class);
                startActivity(intent);
                return true;
            }
        });

        // Remove dividers
        View rootView = getView();
        if (rootView != null) {
            ListView list = (ListView) rootView.findViewById(android.R.id.list);
            list.setDivider(null);
        }
    }
}
