package pt.joaomneto.titancompanion;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Locale;

import pt.joaomneto.titancompanion.fragment.TCPreferenceFragment;
import pt.joaomneto.titancompanion.util.ContextWrapper;

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
            String lang = sharedPreferences.getString("lang", "catala");
            if ("en".equals(lang)) {
                lang = "en_US";
            } else {
                lang = "pt_PT";
            }

            Locale locale = new Locale(lang);
                    getBaseContext().getResources().getDisplayMetrics());
            Context context = ContextWrapper.wrap(this, locale);
            super.attachBaseContext(context);
        }
    }


}
