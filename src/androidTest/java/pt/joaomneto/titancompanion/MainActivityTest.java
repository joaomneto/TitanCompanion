package pt.joaomneto.titancompanion;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import pt.joaomneto.titancompanion.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
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
.atPosition(9);
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
        editText.perform(click());
        
        ViewInteraction editText2 = onView(
allOf(withId(R.id.adventureNameInput),
childAtPosition(
withParent(withId(R.id.pager)),
11),
isDisplayed()));
        editText2.perform(replaceText("huf"), closeSoftKeyboard());
        
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
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
