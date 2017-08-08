package pt.joaomneto.titancompanion;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import pt.joaomneto.titancompanion.util.LocaleHelper;

/**
 * Created by Joao Neto on 31-05-2017.
 */

public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

}
