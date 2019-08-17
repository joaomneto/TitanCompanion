package pt.joaomneto.titancompanion.phase1

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import android.view.View
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.startsWith
import org.junit.Test
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.TCBaseTest
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SPACE_ASSASSIN

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSA : TCBaseTest() {

    override val gamebook = SPACE_ASSASSIN

    fun performChooseWeapons() {

        val button4 = onView(
                allOf<View>(
                        withId(R.id.buttonAddweapon), withText("Add Weapon"),
                        childAtPosition(
                                withParent(withId(R.id.pager)),
                                1
                        ),
                        isDisplayed()
                )
        )
        button4.perform(click())

        val button5 = onView(
                allOf(
                        withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        0
                                ),
                                2
                        ),
                        isDisplayed()
                )
        )
        button5.perform(click())
    }

    @Test
    fun testSuccessfulCreation() {

        performStartAdventure()
        performFillSavegameName()
        performVitalStatisticsRoll()
        performSwipeLeft()
        performChooseWeapons()
        performSwipeRight()
        performSaveAdventureFromCreationScreen()
        assertAdventureLoaded()
    }

    protected fun assertImpossibleToAddWeapons() {
        val string = getString(R.string.saNoWeaponPoints)
        val button5 = onView(allOf<View>(withText(startsWith(string.substring(0, string.length - 4))), isDisplayed()))
        button5.check(matches(isDisplayed()))
    }


}
