package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_CITADEL_OF_CHAOS;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_FOREST_OF_DOOM;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestTCOC extends TCBaseTest{

    @Override
    protected FightingFantasyGamebook getGamebook(){
        return THE_CITADEL_OF_CHAOS;
    }

    @Test
    public void testSuccessfulCreation() {

        performStartAdventure();
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSaveAdventureFromCreationScreen();
        assertAdventureLoaded();

    }


    @Test
    public void testCreationWithoutRoll() {

        performStartAdventure();
        performFillSavegameName();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutSavegame() {

        performStartAdventure();
        performVitalStatisticsRoll();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

}