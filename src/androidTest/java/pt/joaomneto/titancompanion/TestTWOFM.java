package pt.joaomneto.titancompanion;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestTWOFM extends TCBaseTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testTWOFMCreation() {

        launchAdventureCreation("twofm");

        ViewInteraction button3 = onView(
                allOf(withId(R.id.buttonRollStats), withText(R.string.rollStats), isDisplayed()));
        button3.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        withId(R.id.potionList),
                        0),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction button4 = onView(
                allOf(withId(R.id.buttonSaveAdventure), withText(R.string.saveAdventure), isDisplayed()));
        button4.perform(click());

        ViewInteraction button5 = onView(
                allOf(withId(R.id.buttonSavePoint),
                        childAtPosition(
                                allOf(withId(R.id.buttonsBar),
                                        childAtPosition(
                                                withId(R.id.adventureVitalStats),
                                                16)),
                                3),
                        isDisplayed()));
        button5.check(matches(isDisplayed()));

    }

    private void launchAdventureCreation(String adventureCode) {
        ViewInteraction button = onView(
                allOf(withId(R.id.plusSkillButton), withText(R.string.create_new_adventure), isDisplayed()));
        button.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText(getResources().getIdentifier(adventureCode, "string", getPackageName() )),
                        childAtPosition(
                                withId(R.id.gamebookListView),
                                0),
                        isDisplayed()));
        textView.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.buttonCreate), withText(R.string.create_new_adventure), isDisplayed()));
        button2.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.adventureNameInput), isDisplayed()));
        editText.perform(replaceText("test"+adventureCode.toUpperCase()), closeSoftKeyboard());
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
