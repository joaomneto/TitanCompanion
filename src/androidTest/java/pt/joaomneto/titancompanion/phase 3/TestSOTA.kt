package pt.joaomneto.titancompanion.phase2

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CREATURE_OF_HAVOC
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SLAVES_OF_THE_ABYSS
import pt.joaomneto.titancompanion.phase1.TestST

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSOTA : TestST() {

    override val gamebook = SLAVES_OF_THE_ABYSS
}