package pt.joaomneto.titancompanion

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withSpinnerText
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.containsString
import org.junit.Test
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN

@LargeTest
@RunWith(AndroidJUnit4::class)
open class TestTWOFM : TCBaseTest() {

    override val gamebook = THE_WARLOCK_OF_FIRETOP_MOUNTAIN

    @Test
    fun testSuccessfulCreation() {
        performStartAdventure()
        performFillSavegameName()
        performVitalStatisticsRoll()
        performSwipeLeft()
        performChoosePotion()
        assertCorrectPotionDosage()
        performSaveAdventureFromCreationScreen()
        assertAdventureLoaded()
    }

    protected open fun assertCorrectPotionDosage() {
        onView(withId(R.id.potionDosesSpinner)).check(
            matches(
                withSpinnerText(
                    containsString(
                        getString(
                            R.string.potionTwoDoses
                        )
                    )
                )
            )
        )
    }

}
