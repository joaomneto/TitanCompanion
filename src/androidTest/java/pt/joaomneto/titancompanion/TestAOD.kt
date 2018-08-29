package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.TCBaseTest
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.ARMIES_OF_DEATH

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestAOD : TCBaseTest() {
    override val gamebook = ARMIES_OF_DEATH

    @Test
    fun testSuccessfulCreation() {
        performStartAdventure()
        performFillSavegameName()
        performVitalStatisticsRoll()
        performSaveAdventureFromCreationScreen()
        assertAdventureLoaded()
    }
}
