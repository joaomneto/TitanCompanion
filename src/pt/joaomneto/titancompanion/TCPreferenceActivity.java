package pt.joaomneto.titancompanion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;

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

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("lang", sharedPreferences.getString(key, "en_US"));
            editor.commit();

            settings();
        }
    }

    public void settings() {
        Intent intent = new Intent(this, TCPreferenceActivity.class);
        intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, TCPreferenceFragment.class.getName());
        intent.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
        startActivity(intent);
    }


    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences pref =  newBase.getSharedPreferences("lang", MODE_PRIVATE);

        String lang = pref.getString("lang", null);

        Locale locale = new Locale(lang);

        Context context = TCContextWrapper.wrap(newBase, locale);
        super.attachBaseContext(newBase);
    }

}
