package pt.joaomneto.titancompanion;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import java.util.List;
import java.util.Locale;

import pt.joaomneto.titancompanion.fragment.TCPreferenceFragment;
import pt.joaomneto.titancompanion.util.TCContextWrapper;

/**
 * Created by joao on 27/05/17.
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
            recreate();
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(newBase);

        String lang = pref.getString("lang", null);

        Locale locale = new Locale(lang);

        Context context = TCContextWrapper.wrap(newBase, locale);
        super.attachBaseContext(context);
    }


}
