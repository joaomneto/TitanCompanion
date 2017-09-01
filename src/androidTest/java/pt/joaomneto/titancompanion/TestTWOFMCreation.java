package pt.joaomneto.titancompanion;


import android.support.test.runner.AndroidJUnit4;
import android.support.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestTWOFMCreation extends TCBaseTest{



    @Test
    public void testSuccessfulCreation() {

        performStartAdventure(THE_WARLOCK_OF_FIRETOP_MOUNTAIN);
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performChoosePotion();
        performSaveAdventureFromCreationScreen();
        assertAdventureLoaded();

    }

    @Test
    public void testCreationWithoutPotion() {

        performStartAdventure(THE_WARLOCK_OF_FIRETOP_MOUNTAIN);
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutRoll() {

        performStartAdventure(THE_WARLOCK_OF_FIRETOP_MOUNTAIN);
        performFillSavegameName();
        performSwipeLeft();
        performChoosePotion();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutSavegame() {

        performStartAdventure(THE_WARLOCK_OF_FIRETOP_MOUNTAIN);
        performVitalStatisticsRoll();
        performSwipeLeft();
        performChoosePotion();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }




}
