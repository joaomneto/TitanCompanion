package pt.joaomneto.titancompanion;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction button = onView(
                allOf(withId(android.R.id.button2), withText("Close"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.plusValueButton), withText("Start New Adventure"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        button2.perform(click());

        DataInteraction textView = onData(anything())
                .inAdapterView(allOf(withId(R.id.gamebookListView),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)))
                .atPosition(11);
        textView.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.buttonCreate), withText("Start New Adventure"),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                1),
                        isDisplayed()));
        button3.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.adventureNameInput),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                11),
                        isDisplayed()));
        editText.perform(click());

        ViewInteraction button4 = onView(
                allOf(withId(R.id.buttonRollStats), withText("Reroll Statistics"),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                9),
                        isDisplayed()));
        button4.perform(click());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.adventureNameInput),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                11),
                        isDisplayed()));
        editText2.perform(replaceText("g"), closeSoftKeyboard());

        pressBack();

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.adventureNameInput), withText("g"),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                11),
                        isDisplayed()));
        editText3.perform(replaceText("gggd"));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.adventureNameInput), withText("gggd"),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                11),
                        isDisplayed()));
        editText4.perform(closeSoftKeyboard());

        pressBack();

        ViewInteraction button5 = onView(
                allOf(withId(R.id.buttonAddweapon), withText("Add Weapon"),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                1),
                        isDisplayed()));
        button5.perform(click());

        ViewInteraction button6 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        button6.perform(click());

    }

    private static Matcher<View> childAtPosition(
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
}
