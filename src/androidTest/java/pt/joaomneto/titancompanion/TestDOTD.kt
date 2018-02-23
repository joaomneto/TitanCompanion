package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4

import org.junit.runner.RunWith

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.DEMONS_OF_THE_DEEP

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestDOTD : TestTFOD() {

    override val gamebook = DEMONS_OF_THE_DEEP

}
