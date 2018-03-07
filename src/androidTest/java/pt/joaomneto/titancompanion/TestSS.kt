package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SCORPION_SWAMP

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSS : TestST() {

    override val gamebook = SCORPION_SWAMP
}