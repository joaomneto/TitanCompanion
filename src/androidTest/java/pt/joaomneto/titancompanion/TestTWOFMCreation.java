package pt.joaomneto.titancompanion;


import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestTWOFMCreation extends TCBaseTest{



    @Test
    public void testTWOFM() {


        performStartAdventure(THE_WARLOCK_OF_FIRETOP_MOUNTAIN);
        performVitalStatisticsRollAndSavegameName();
        performSwipeLeft();
        performChoosePotion();
        performSaveAdventureFromCreationScreen();


//        AdventureVisibilityIdlingResource idlingResource = new AdventureVisibilityIdlingResource((Adventure) getActivityInstance());
//        IdlingRegistry.getInstance().register(idlingResource);


        assertAdventureLoaded();

//        IdlingRegistry.getInstance().unregister(idlingResource);

    }


}
