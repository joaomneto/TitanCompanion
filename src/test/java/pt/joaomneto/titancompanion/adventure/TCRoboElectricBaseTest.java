package pt.joaomneto.titancompanion.adventure;

import android.annotation.SuppressLint;
import android.util.ArraySet;
import android.view.View;
import org.junit.After;
import org.junit.Before;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.util.ReflectionHelpers;

import java.util.List;

import static org.robolectric.Robolectric.flushBackgroundThreadScheduler;
import static org.robolectric.Robolectric.flushForegroundThreadScheduler;
import static org.robolectric.shadows.ShadowApplication.runBackgroundTasks;
import static org.robolectric.shadows.ShadowLooper.runUiThreadTasksIncludingDelayedTasks;

public abstract class TCRoboElectricBaseTest {
    @Before
    public void setUp() {
        ShadowApplication.getInstance().declareActionUnbindable("com.google.android.gms.analytics.service.START");
    }

    @After
    public void tearDown() throws Exception {
        // https://github.com/robolectric/robolectric/issues/1700
        // https://github.com/robolectric/robolectric/issues/2068
        // https://github.com/robolectric/robolectric/issues/2584
        this.resetBackgroundThread();
        this.resetWindowManager();
        this.finishThreads();
    }

    public void finishThreads() {
        runBackgroundTasks();
        flushForegroundThreadScheduler();
        flushBackgroundThreadScheduler();
        runUiThreadTasksIncludingDelayedTasks();
    }

    // https://github.com/robolectric/robolectric/pull/1741
    private void resetBackgroundThread() throws Exception {
        final Class<?> btclass = Class.forName("com.android.internal.os.BackgroundThread");
        final Object backgroundThreadSingleton = ReflectionHelpers.getStaticField(btclass, "sInstance");
        if (backgroundThreadSingleton != null) {
            btclass.getMethod("quit").invoke(backgroundThreadSingleton);
            ReflectionHelpers.setStaticField(btclass, "sInstance", null);
            ReflectionHelpers.setStaticField(btclass, "sHandler", null);
        }
    }

    // https://github.com/robolectric/robolectric/issues/2068#issue-109132096
    @SuppressLint("NewApi")
    private void resetWindowManager() {
        final Class<?> clazz = ReflectionHelpers.loadClass(this.getClass().getClassLoader(), "android.view.WindowManagerGlobal");
        final Object instance = ReflectionHelpers.callStaticMethod(clazz, "getInstance");

        // We essentially duplicate what's in {@link WindowManagerGlobal#closeAll} with what's below.
        // The closeAll method has a bit of a bug where it's iterating through the "roots" but
        // bases the number of objects to iterate through by the number of "views." This can result in
        // an {@link java.lang.IndexOutOfBoundsException} being thrown.
        final Object lock = ReflectionHelpers.getField(instance, "mLock");

        final List<Object> roots = ReflectionHelpers.getField(instance, "mRoots");
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (lock) {
            for (int i = 0; i < roots.size(); i++) {
                ReflectionHelpers.callInstanceMethod(instance, "removeViewLocked",
                        ReflectionHelpers.ClassParameter.from(int.class, i),
                        ReflectionHelpers.ClassParameter.from(boolean.class, false));
            }
        }

        // Views will still be held by this array. We need to clear it out to ensure
        // everything is released.
        final ArraySet<View> dyingViews = ReflectionHelpers.getField(instance, "mDyingViews");
        dyingViews.clear();
    }
}
