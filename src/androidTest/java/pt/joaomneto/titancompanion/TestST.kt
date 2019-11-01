package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.TCBaseTest
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.STARSHIP_TRAVELLER

@LargeTest
@RunWith(AndroidJUnit4::class)
open class TestST : TCBaseTest() {

    override val gamebook = STARSHIP_TRAVELLER

    @Test
    fun testSuccessfulCreation() {
        performStartAdventure()
        performFillSavegameName()
        performVitalStatisticsRoll()
        performSaveAdventureFromCreationScreen()
        assertAdventureLoaded()
        testVitalStatisticsFragment()
    }
}
