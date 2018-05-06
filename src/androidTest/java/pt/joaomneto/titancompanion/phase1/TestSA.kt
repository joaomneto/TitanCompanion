package pt.joaomneto.titancompanion.phase1

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withClassName
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withParent
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
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
