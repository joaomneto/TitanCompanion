package pt.joaomneto.titancompanion;


import android.support.test.runner.AndroidJUnit4;
import android.support.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestTWOFM extends TCBaseTest{

    @Override
    protected FightingFantasyGamebook getGamebook(){
        return THE_WARLOCK_OF_FIRETOP_MOUNTAIN;
    }

    @Test
    public void testSuccessfulCreation() {

        performStartAdventure();
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performChoosePotion();
        performSaveAdventureFromCreationScreen();
        assertAdventureLoaded();

    }

    @Test
    public void testCreationWithoutPotion() {

        performStartAdventure();
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutRoll() {

        performStartAdventure();
        performFillSavegameName();
        performSwipeLeft();
        performChoosePotion();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutSavegame() {

        performStartAdventure();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performChoosePotion();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }




}
