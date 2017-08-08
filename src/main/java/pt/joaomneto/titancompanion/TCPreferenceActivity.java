package pt.joaomneto.titancompanion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;

import java.util.List;

import pt.joaomneto.titancompanion.fragment.TCPreferenceFragment;
import pt.joaomneto.titancompanion.util.LocaleHelper;

/**
 * Created by Joao Neto on 27/05/17.
 */

public class TCPreferenceActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.headers_preference, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return TCPreferenceFragment.class.getName().equals(fragmentName);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("lang")) {
            LocaleHelper.setLocale(this, sharedPreferences.getString("lang", null));
            Intent i = getBaseContext().getPackageManager().
                    getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


}
