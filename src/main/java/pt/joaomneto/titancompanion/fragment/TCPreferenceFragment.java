package pt.joaomneto.titancompanion.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import pt.joaomneto.titancompanion.R;
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.TCPreferenceActivity;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * Created by Joao Neto on 27/05/17.
 */

public class TCPreferenceFragment extends PreferenceFragment {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener((TCPreferenceActivity) this.getActivity());

        Preference button = findPreference(getString(R.string.savegameImportButton));
        button.setOnPreferenceClickListener(preference -> {

            if (ContextCompat.checkSelfPermission(this.getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this.getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {


                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }

            return true;
        });

    }

}
