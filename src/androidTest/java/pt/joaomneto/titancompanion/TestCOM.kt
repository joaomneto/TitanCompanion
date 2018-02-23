package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4

import org.junit.runner.RunWith

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CHASMS_OF_MALICE
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SCORPION_SWAMP

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestCOM : TestST() {

    override val gamebook = CHASMS_OF_MALICE


}