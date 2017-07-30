package pt.joaomneto.titancompanion.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.TCPreferenceActivity;

/**
 * Created by joao on 27/05/17.
 */

public class TCPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);




        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener((TCPreferenceActivity)this.getActivity());

    }
}
