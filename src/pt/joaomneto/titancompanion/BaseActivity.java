package pt.joaomneto.titancompanion;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import java.util.Locale;

import pt.joaomneto.titancompanion.util.TCContextWrapper;

/**
 * Created by 962633 on 31-05-2017.
 */

public class BaseActivity extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(newBase);

        String lang = pref.getString("lang", null);

        Locale locale = new Locale(lang);

        Context context = TCContextWrapper.wrap(newBase, locale);
        super.attachBaseContext(newBase);
    }

}
