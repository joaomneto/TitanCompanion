package pt.joaomneto.titancompanion.phase2

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CREATURE_OF_HAVOC
import pt.joaomneto.titancompanion.phase1.TestST

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestCOH : TestST() {

    override val gamebook = CREATURE_OF_HAVOC
}
