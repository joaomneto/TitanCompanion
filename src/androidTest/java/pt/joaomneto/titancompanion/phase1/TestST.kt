package pt.joaomneto.titancompanion.phase1

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
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
