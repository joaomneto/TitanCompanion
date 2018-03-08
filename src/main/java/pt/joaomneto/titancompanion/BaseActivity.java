package pt.joaomneto.titancompanion;

import android.app.Activity;
import android.content.Context;
import pt.joaomneto.titancompanion.util.LocaleHelper;

/**
 * Created by Joao Neto on 31-05-2017.
 */

public class BaseActivity extends Activity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

}
