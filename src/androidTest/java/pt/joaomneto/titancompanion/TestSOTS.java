package pt.joaomneto.titancompanion;


import android.support.test.espresso.DataInteraction;
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
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SWORD_OF_THE_SAMURAI;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestSOTS extends TCBaseTest {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return SWORD_OF_THE_SAMURAI;
    }


    public void performChoosemartialArt() {

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.skillList),
                        Companion.childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)))
                .atPosition(0);
        linearLayout.perform(click());
    }

    @Test
    public void testSuccessfulCreation() {

        performStartAdventure();
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performChoosemartialArt();
        performSwipeRight();
        performSaveAdventureFromCreationScreen();
        assertAdventureLoaded();

    }


    @Test
    public void testCreationWithoutRoll() {

        performStartAdventure();
        performFillSavegameName();
        performSwipeLeft();
        performChoosemartialArt();
        performSwipeRight();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutSavegame() {

        performStartAdventure();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performChoosemartialArt();
        performSwipeRight();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutMartialArt() {

        performStartAdventure();
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

}
