package pt.joaomneto.titancompanion

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withClassName
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import android.view.View
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.junit.Test
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SWORD_OF_THE_SAMURAI

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSOTS : TCBaseTest() {

    override val gamebook = SWORD_OF_THE_SAMURAI

    fun performChoosemartialArt() {

        val linearLayout = onData(anything())
            .inAdapterView(
                allOf<View>(
                    withId(R.id.skillList),
                    TCBaseTest.Companion.childAtPosition(
                        withClassName(`is`("android.widget.RelativeLayout")),
                        1
                    )
                )
            )
            .atPosition(0)
        linearLayout.perform(click())
    }

    @Test
    fun testSuccessfulCreation() {

        performStartAdventure()
        performFillSavegameName()
        performVitalStatisticsRoll()
        performSwipeLeft()
        performChoosemartialArt()
        performSwipeRight()
        performSaveAdventureFromCreationScreen()
        assertAdventureLoaded()
    }

    @Test
    fun testCreationWithoutRoll() {

        performStartAdventure()
        performFillSavegameName()
        performSwipeLeft()
        performChoosemartialArt()
        performSwipeRight()
        performSaveAdventureFromCreationScreen()
        assertInvalidAdventureCreation()
    }

    @Test
    fun testCreationWithoutSavegame() {

        performStartAdventure()
        performVitalStatisticsRoll()
        performSwipeLeft()
        performChoosemartialArt()
        performSwipeRight()
        performSaveAdventureFromCreationScreen()
        assertInvalidAdventureCreation()
    }

    @Test
    fun testCreationWithoutMartialArt() {

        performStartAdventure()
        performFillSavegameName()
        performVitalStatisticsRoll()
        performSaveAdventureFromCreationScreen()
        assertInvalidAdventureCreation()
    }
}
