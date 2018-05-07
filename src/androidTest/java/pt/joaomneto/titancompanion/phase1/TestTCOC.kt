package pt.joaomneto.titancompanion.phase1

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
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.TCBaseTest
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_CITADEL_OF_CHAOS

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestTCOC : TCBaseTest() {

    override val gamebook = THE_CITADEL_OF_CHAOS

    fun performChooseSpells() {
        val textView2 = onData(anything())
                .inAdapterView(
                        allOf<View>(
                                withId(R.id.spellListView),
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        1
                                )
                        )
                )
                .atPosition(0)
        textView2.perform(click())

        val textView3 = onData(anything())
                .inAdapterView(
                        allOf<View>(
                                withId(R.id.spellListView),
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        1
                                )
                        )
                )
                .atPosition(0)
        textView3.perform(click())

        val textView4 = onData(anything())
                .inAdapterView(
                        allOf<View>(
                                withId(R.id.spellListView),
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        1
                                )
                        )
                )
                .atPosition(1)
        textView4.perform(click())

        val textView5 = onData(anything())
                .inAdapterView(
                        allOf<View>(
                                withId(R.id.spellListView),
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        1
                                )
                        )
                )
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
    }


}
