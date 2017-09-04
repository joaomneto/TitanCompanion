package pt.joaomneto.titancompanion;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SPACE_ASSASSIN;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestSA extends TCBaseTest {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return SPACE_ASSASSIN;
    }


    public void performChooseWeapons() {

        ViewInteraction button4 = onView(
                allOf(withId(R.id.buttonAddweapon), withText("Add Weapon"),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                1),
                        isDisplayed()));
        button4.perform(click());

        ViewInteraction button5 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        button5.perform(click());
    }

    @Test
    public void testSuccessfulCreation() {

        performStartAdventure();
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performChooseWeapons();
        performSwipeRight();
        performSaveAdventureFromCreationScreen();
        assertAdventureLoaded();

    }


    protected void assertImpossibleToAddWeapons() {
        ViewInteraction button5 = onView(allOf(withText(startsWith(getString(R.string.saNoWeaponPoints))), isDisplayed()));
        button5.check(matches(isDisplayed()));
    }

    @Test
    public void testCreationWithoutRoll() {

        performStartAdventure();
        performFillSavegameName();
        performSwipeLeft();
        performChooseWeapons();
        assertImpossibleToAddWeapons();

    }

    @Test
    public void testCreationWithoutSavegame() {

        performStartAdventure();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performChooseWeapons();
        performSwipeRight();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutWeapons() {

        performStartAdventure();
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

}
