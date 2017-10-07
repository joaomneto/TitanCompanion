package pt.joaomneto.titancompanion;


import android.support.test.runner.AndroidJUnit4;
import android.support.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.Matchers.containsString;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestTWOFM extends TCBaseTest {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return THE_WARLOCK_OF_FIRETOP_MOUNTAIN;
    }

    @Test
    public void testSuccessfulCreation() {

        performStartAdventure();
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performChoosePotion();
        assertCorrectPotionDosage();
        performSaveAdventureFromCreationScreen();
        assertAdventureLoaded();

    }

    protected void assertCorrectPotionDosage() {
        onView(withId(R.id.potionDosesSpinner)).check(matches(withSpinnerText(containsString(getString(R.string.potionTwoDoses)))));
    }

    @Test
    public void testCreationWithoutPotion() {

        performStartAdventure();
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSwipeLeft();
        assertCorrectPotionDosage();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutRoll() {

        performStartAdventure();
        performFillSavegameName();
        performSwipeLeft();
        performChoosePotion();
        assertCorrectPotionDosage();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutSavegame() {

        performStartAdventure();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performChoosePotion();
        assertCorrectPotionDosage();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }


}
