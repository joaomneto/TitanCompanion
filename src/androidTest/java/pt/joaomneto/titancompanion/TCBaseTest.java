package pt.joaomneto.titancompanion;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;

/**
 * Created by 962633 on 03-08-2017.
 */

public class TCBaseTest {

    protected Resources getResources() {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources();
    }

    protected String getPackageName() {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getPackageName();
    }

}
