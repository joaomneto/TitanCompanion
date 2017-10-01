package pt.joaomneto.titancompanion;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.APPOINTMENT_WITH_F_E_A_R;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SPACE_ASSASSIN;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestAWF extends TCBaseTest {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return APPOINTMENT_WITH_F_E_A_R;
    }


    public void performChooseSuperpower() {

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.superpowerList),
                        childAtPosition(
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
        performChooseSuperpower();
        performSwipeRight();
        performSaveAdventureFromCreationScreen();
        assertAdventureLoaded();

    }


    @Test
    public void testCreationWithoutRoll() {

        performStartAdventure();
        performFillSavegameName();
        performSwipeLeft();
        performChooseSuperpower();
        performSwipeRight();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutSavegame() {

        performStartAdventure();
        performVitalStatisticsRoll();
        performSwipeLeft();
        performChooseSuperpower();
        performSwipeRight();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

    @Test
    public void testCreationWithoutSuperpower() {

        performStartAdventure();
        performFillSavegameName();
        performVitalStatisticsRoll();
        performSaveAdventureFromCreationScreen();
        assertInvalidAdventureCreation();

    }

}
