//package pt.joaomneto.titancompanion.adventure
//
//import android.annotation.SuppressLint
//import android.app.Application
//import android.content.Context
//import android.os.Build
//import android.util.ArraySet
//import android.view.View
//
//import androidx.test.core.app.ApplicationProvider
//
//import org.junit.After
//import org.junit.Before
//import org.robolectric.util.ReflectionHelpers
//import java.util.Objects
//
//import org.robolectric.Robolectric.flushBackgroundThreadScheduler
//import org.robolectric.Robolectric.flushForegroundThreadScheduler
//import org.robolectric.Shadows.shadowOf
//import org.robolectric.annotation.Config
//import org.robolectric.shadows.ShadowApplication.runBackgroundTasks
//import org.robolectric.shadows.ShadowLooper.runUiThreadTasksIncludingDelayedTasks
//
//
//abstract class TCRoboElectricBaseTest {
//    @Before
//    fun setUp() {
//        shadowOf(ApplicationProvider.getApplicationContext<Context>() as Application).declareActionUnbindable("com.google.android.gms.analytics.service.START")
//    }
//
//    @After
//    @Throws(Exception::class)
//    fun tearDown() {
//        // https://github.com/robolectric/robolectric/issues/1700
//        // https://github.com/robolectric/robolectric/issues/2068
//        // https://github.com/robolectric/robolectric/issues/2584
//        this.resetBackgroundThread()
//        this.resetWindowManager()
//        this.finishThreads()
//    }
//
//    private fun finishThreads() {
//        runBackgroundTasks()
//        flushForegroundThreadScheduler()
//        flushBackgroundThreadScheduler()
//        runUiThreadTasksIncludingDelayedTasks()
//    }
//
//    // https://github.com/robolectric/robolectric/pull/1741
//    @Throws(Exception::class)
//    private fun resetBackgroundThread() {
//        val btclass = Class.forName("com.android.internal.os.BackgroundThread")
//        val backgroundThreadSingleton = ReflectionHelpers.getStaticField<Any>(btclass, "sInstance")
//        if (backgroundThreadSingleton != null) {
//            btclass.getMethod("quit").invoke(backgroundThreadSingleton)
//            ReflectionHelpers.setStaticField(btclass, "sInstance", null)
//            ReflectionHelpers.setStaticField(btclass, "sHandler", null)
//        }
//    }
//
//    // https://github.com/robolectric/robolectric/issues/2068#issue-109132096
//    @SuppressLint("NewApi")
//    private fun resetWindowManager() {
//        val clazz = ReflectionHelpers.loadClass(
//            Objects.requireNonNull(this.javaClass.classLoader),
//            "android.view.WindowManagerGlobal"
//        )
//        val instance = ReflectionHelpers.callStaticMethod<Any>(clazz, "getInstance")
//
//        // We essentially duplicate what's in {@link WindowManagerGlobal#closeAll} with what's below.
//        // The closeAll method has a bit of a bug where it's iterating through the "roots" but
//        // bases the number of objects to iterate through by the number of "views." This can result in
//        // an {@link java.lang.IndexOutOfBoundsException} being thrown.
//        val lock = ReflectionHelpers.getField<Any>(instance, "mLock")
//
//        val roots = ReflectionHelpers.getField<List<Any>>(instance, "mRoots")
//
//        synchronized(lock) {
//            for (i in roots.indices) {
//                ReflectionHelpers.callInstanceMethod<Any>(
//                    instance, "removeViewLocked",
//                    ReflectionHelpers.ClassParameter.from(Int::class.javaPrimitiveType, i),
//                    ReflectionHelpers.ClassParameter.from(Boolean::class.javaPrimitiveType, false)
//                )
//            }
//        }
//
//        // Views will still be held by this array. We need to clear it out to ensure
//        // everything is released.
//        val dyingViews = ReflectionHelpers.getField<ArraySet<View>>(instance, "mDyingViews")
//        dyingViews.clear()
//    }
//}
