package pt.joaomneto.titancompanion

import android.support.test.espresso.ViewInteraction
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4

import org.junit.runner.RunWith

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withParent
import org.hamcrest.Matchers.allOf
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.HOUSE_OF_HELL

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestHOH : TestST() {

    override val gamebook = HOUSE_OF_HELL


}