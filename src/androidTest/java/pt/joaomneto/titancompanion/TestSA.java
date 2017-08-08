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

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestSA {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSA() {
        ViewInteraction button = onView(
                allOf(withId(R.id.plusSkillButton), withText("Start New Adventure"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        button.perform(click());

        DataInteraction textView = onData(anything())
                .inAdapterView(allOf(withId(R.id.gamebookListView),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)))
                .atPosition(11);
        textView.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.buttonCreate), withText("Start New Adventure"),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                1),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.adventureNameInput),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                11),
                        isDisplayed()));
        editText.perform(replaceText("ffs"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.adventureNameInput), withText("ffs"),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                11),
                        isDisplayed()));
        editText3.perform(closeSoftKeyboard());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.buttonRollStats), withText("Reroll Statistics"),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                9),
                        isDisplayed()));
        button3.perform(click());

        ViewInteraction button4 = onView(
                allOf(withId(R.id.buttonAddweapon), withText("Add Weapon"),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                1),
                        isDisplayed()));
        button4.perform(click());

        ViewInteraction spinner = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.custom),
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        spinner.perform(click());

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
