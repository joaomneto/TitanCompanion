package pt.joaomneto.titancompanion

import android.view.View
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.junit.Test
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.TCBaseTest
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.APPOINTMENT_WITH_F_E_A_R

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestAWF : TCBaseTest() {

    override val gamebook = APPOINTMENT_WITH_F_E_A_R

    fun performChooseSuperpower() {
        val linearLayout = onData(anything())
            .inAdapterView(
                allOf<View>(
                    withId(R.id.superpowerList),
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
        performChooseSuperpower()
        performSwipeRight()
        performSaveAdventureFromCreationScreen()
        assertAdventureLoaded()
    }
}
