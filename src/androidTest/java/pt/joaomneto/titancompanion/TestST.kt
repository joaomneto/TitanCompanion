package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.STARSHIP_TRAVELLER
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_FOREST_OF_DOOM
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN

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
        performSwipeOverAllScreens()
    }

    @Test
    fun testCreationWithoutRoll() {

        performStartAdventure()
        performFillSavegameName()
        performSaveAdventureFromCreationScreen()
        assertInvalidAdventureCreation()

    }

    @Test
    fun testCreationWithoutSavegame() {

        performStartAdventure()
        performVitalStatisticsRoll()
        performSaveAdventureFromCreationScreen()
        assertInvalidAdventureCreation()

    }

}
