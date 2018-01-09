package pt.joaomneto.titancompanion;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;

import android.util.DisplayMetrics;
import android.view.WindowManager;
import pt.joaomneto.titancompanion.util.LocaleHelper;

/**
 * Created by Joao Neto on 31-05-2017.
 */

public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustFontScale(new Configuration(newConfig));
    }


    public void adjustFontScale(Configuration configuration) {
        if (configuration != null && configuration.fontScale != 1.0) {
            configuration.fontScale = (float) 1.0;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getBaseContext().getResources().updateConfiguration(configuration, metrics);
        }
    }

}
