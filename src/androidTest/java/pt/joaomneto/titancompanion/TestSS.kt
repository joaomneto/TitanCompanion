package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SCORPION_SWAMP
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_FOREST_OF_DOOM
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSS : TestST() {

    override val gamebook = SCORPION_SWAMP


}