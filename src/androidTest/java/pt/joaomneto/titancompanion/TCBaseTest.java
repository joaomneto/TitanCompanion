package pt.joaomneto.titancompanion;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import java.util.Collection;
import java.util.Iterator;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by Joao Neto on 03-08-2017.
 */

public abstract class TCBaseTest{


    protected Resources getResources() {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources();
    }

    protected String getPackageName() {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getPackageName();
    }

    protected Activity getActivityInstance(){
        final Activity[] currentActivity = {null};

        getInstrumentation().runOnMainSync(new Runnable(){
            public void run(){
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                Iterator<Activity> it = resumedActivity.iterator();
                currentActivity[0] = it.next();
            }
        });

        return currentActivity[0];
    }

}
