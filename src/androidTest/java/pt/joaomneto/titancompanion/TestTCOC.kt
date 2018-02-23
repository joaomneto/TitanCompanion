package pt.joaomneto.titancompanion

import android.support.test.espresso.DataInteraction
import android.support.test.espresso.Espresso
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withClassName
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.view.View
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_CITADEL_OF_CHAOS
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_FOREST_OF_DOOM
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestTCOC : TCBaseTest() {

    override val gamebook = THE_CITADEL_OF_CHAOS

    fun performChooseSpells() {
        val textView2 = onData(anything())
            .inAdapterView(allOf<View>(withId(R.id.spellListView),
                TCBaseTest.Companion.childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    1)))
            .atPosition(0)
        textView2.perform(click())

        val textView3 = onData(anything())
            .inAdapterView(allOf<View>(withId(R.id.spellListView),
                TCBaseTest.Companion.childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    1)))
            .atPosition(0)
        textView3.perform(click())

        val textView4 = onData(anything())
            .inAdapterView(allOf<View>(withId(R.id.spellListView),
                TCBaseTest.Companion.childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    1)))
            .atPosition(1)
        textView4.perform(click())

        val textView5 = onData(anything())
            .inAdapterView(allOf<View>(withId(R.id.spellListView),
                TCBaseTest.Companion.childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    1)))
            .atPosition(1)
        textView5.perform(click())
    }

    @Test
    fun testSuccessfulCreation() {

        performStartAdventure()
        performFillSavegameName()
        performVitalStatisticsRoll()
        performSwipeLeft()
        performChooseSpells()
        performSaveAdventureFromCreationScreen()
        assertAdventureLoaded()
        performSwipeOverAllScreens()
    }

    @Test
    fun testCreationWithoutRoll() {

        performStartAdventure()
        performFillSavegameName()
        performSwipeLeft()
        performChooseSpells()
        performSwipeRight()
        performSaveAdventureFromCreationScreen()
        assertInvalidAdventureCreation()

    }

    @Test
    fun testCreationWithoutSavegame() {

        performStartAdventure()
        performVitalStatisticsRoll()
        performSwipeLeft()
        performChooseSpells()
        performSaveAdventureFromCreationScreen()
        assertInvalidAdventureCreation()

    }

    @Test
    fun testCreationWithoutPotions() {

        performStartAdventure()
        performFillSavegameName()
        performVitalStatisticsRoll()
        performSaveAdventureFromCreationScreen()
        assertInvalidAdventureCreation()

    }

}
