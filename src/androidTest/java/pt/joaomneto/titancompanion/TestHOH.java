package pt.joaomneto.titancompanion;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.HOUSE_OF_HELL;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestHOH extends TestST {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return HOUSE_OF_HELL;
    }



}