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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
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
public class TestTWOFMCreation {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testTWOFM() {
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
                .atPosition(0);
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
                                10),
                        isDisplayed()));
        editText.perform(replaceText("espresso"), closeSoftKeyboard());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.buttonRollStats), withText("Reroll Statistics"),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                8),
                        isDisplayed()));
        button3.perform(click());


        onView(withId(R.id.pager)).perform(swipeLeft());

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.potionList),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)))
                .atPosition(0);
        linearLayout.perform(click());

        ViewInteraction button4 = onView(
                allOf(withId(R.id.buttonSaveAdventure), withText("Save Adventure"),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                2),
                        isDisplayed()));
        button4.perform(click());

        Thread.sleep(2000);

        ViewInteraction button5 = onView(allOf(withId(R.id.buttonSavePoint), isDisplayed()));
        button5.check(matches(isDisplayed()));

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
