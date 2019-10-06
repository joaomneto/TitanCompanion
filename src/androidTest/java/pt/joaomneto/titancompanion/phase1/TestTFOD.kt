package pt.joaomneto.titancompanion.phase1

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.containsString
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_FOREST_OF_DOOM
import pt.joaomneto.titancompanion.util.LocaleHelper

@LargeTest
@RunWith(AndroidJUnit4::class)
open class TestTFOD : TestTWOFM() {

    override val gamebook = THE_FOREST_OF_DOOM

    override fun assertCorrectPotionDosage() {
        val language = LocaleHelper.getLanguage(mActivityTestRule.activity)
        val string = getString(if ("fr" == language) R.string.potionTwoDoses else R.string.potionOneDose)
        onView(withId(R.id.potionDosesSpinner)).check(matches(withSpinnerText(containsString(string))))
    }
}
