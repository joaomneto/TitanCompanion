package pt.joaomneto.titancompanion

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.containsString
import org.junit.Test
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.TCBaseTest
import pt.joaomneto.titancompanion.adventure.Adventure
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
//
//    @Test
// //    fun testCreationWithoutPotion() {
//
//        performStartAdventure()
//        performFillSavegameName()
//        performVitalStatisticsRoll()
//        performSwipeLeft()
//        assertCorrectPotionDosage()
//        performSaveAdventureFromCreationScreen()
//        assertInvalidAdventureCreation()
//    }
//
//    @Test
// //    fun testCreationWithoutRoll() {
//
//        performStartAdventure()
//        performFillSavegameName()
//        performSwipeLeft()
//        performChoosePotion()
//        assertCorrectPotionDosage()
//        performSaveAdventureFromCreationScreen()
//        assertInvalidAdventureCreation()
//    }
//
//    @Test
// //    fun testCreationWithoutSavegame() {
//
//        performStartAdventure()
//        performVitalStatisticsRoll()
//        performSwipeLeft()
//        performChoosePotion()
//        assertCorrectPotionDosage()
//        performSaveAdventureFromCreationScreen()
//        assertInvalidAdventureCreation()
//    }

    fun testProvisionStat(adventure: Adventure) {
        testIncrementalStat(
            adventure,
            R.id.minusProvisionsButton,
            R.id.plusProvisionsButton,
            R.id.provisionsValue,
            Adventure::provisions
        )
    }
}
