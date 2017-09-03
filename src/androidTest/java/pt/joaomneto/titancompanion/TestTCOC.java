package pt.joaomneto.titancompanion;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_CITADEL_OF_CHAOS;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_FOREST_OF_DOOM;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestTCOC extends TCBaseTest {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return THE_CITADEL_OF_CHAOS;
    }


    public void performChooseSpells() {
        DataInteraction textView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.spellListView),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(0);
        textView2.perform(click());

        DataInteraction textView3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.spellListView),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(0);
        textView3.perform(click());

        DataInteraction textView4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.spellListView),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(1);
        textView4.perform(click());

        DataInteraction textView5 = onData(anything())
                .inAdapterView(allOf(withId(R.id.spellListView),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(1);
        textView5.perform(click());
    }

    @Test
    public void testSuccessfulCreation() {

        performStartAdventure();
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performChooseSpells();
        performSaveAdventureFromCreationScreen();
        assertAdventureLoaded();

    }


    @Test
    public void testCreationWithoutRoll() {

        performStartAdventure();
        performFillSavegameName();
        performSwipeLeft();
        performChooseSpells();
        performSwipeRight();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutSavegame() {

        performStartAdventure();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performChooseSpells();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutPotions() {

        performStartAdventure();
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

}
