package pt.joaomneto.titancompanion

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
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
                                childAtPosition(
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


}
