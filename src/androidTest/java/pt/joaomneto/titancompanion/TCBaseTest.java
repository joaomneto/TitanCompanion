package pt.joaomneto.titancompanion;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;

import java.util.Collection;
import java.util.Iterator;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by Joao Neto on 03-08-2017.
 */

public abstract class TCBaseTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    protected abstract FightingFantasyGamebook getGamebook();

    protected static Activity getActivityInstance() {
        final Activity[] currentActivity = {null};

        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                Iterator<Activity> it = resumedActivity.iterator();
                currentActivity[0] = it.next();
            }
        });

        return currentActivity[0];
    }

    protected static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    protected Resources getResources() {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources();
    }

    protected String getPackageName() {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getPackageName();
    }

    protected void performSwipeLeft() {
        onView(withId(R.id.pager)).perform(swipeLeft());
    }

    protected void performSwipeUp() {
        onView(withId(R.id.pager)).perform(swipeUp());
    }

    protected void assertAdventureLoaded() {
        ViewInteraction button5 = onView(allOf(withId(R.id.buttonSavePoint), isDisplayed()));
        button5.check(matches(isDisplayed()));
    }

    protected void assertInvalidAdventureCreation() {
        ViewInteraction button5 = onView(allOf(withText(startsWith(getString(R.string.someParametersMIssing))), isDisplayed()));
        button5.check(matches(isDisplayed()));
    }

    protected void performSaveAdventureFromCreationScreen() {
        ViewInteraction button;
        button = onView(allOf(withText(getString(R.string.saveAdventure)), isDisplayed()));
        button.perform(click());
    }

    protected void performChoosePotion() {
        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.potionList),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)))
                .atPosition(0);
        linearLayout.perform(click());
    }

    protected void performVitalStatisticsRoll() {
        performSwipeUp();
        ViewInteraction button;
        button = onView(allOf(withText(getString(R.string.rollStats)), isDisplayed()));
        button.perform(click());
    }

    protected void performFillSavegameName() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.adventureNameInput), isDisplayed()));
        editText.perform(replaceText("espresso"), closeSoftKeyboard());
    }

    protected void performStartAdventure() {
        ViewInteraction button = onView(allOf(withText(getString(R.string.create_new_adventure)), isDisplayed()));
        button.perform(click());


        //Clicks over the proper book using the property order of the GamebookEnum
        DataInteraction textView = onData(anything())
                .inAdapterView(allOf(withId(R.id.gamebookListView),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)))
                .atPosition(getGamebook().getOrder()-1);
        textView.perform(click());

        button = onView(allOf(withText(getString(R.string.create_new_adventure)), isDisplayed()));
        button.perform(click());
    }

    protected String getString(int resourceId){
        return mActivityTestRule.getActivity().getString(resourceId);
    }

}
