package pt.joaomneto.titancompanion.phase1

import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SCORPION_SWAMP

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSS : TestST() {

    override val gamebook = SCORPION_SWAMP
}
