package pt.joaomneto.titancompanion;

import android.app.Application;
import android.content.Context;
import pt.joaomneto.titancompanion.util.LocaleHelper;


public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
}