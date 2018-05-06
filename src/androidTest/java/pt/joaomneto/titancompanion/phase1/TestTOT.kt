package pt.joaomneto.titancompanion.phase1

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.TEMPLE_OF_TERROR

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestTOT : TestST() {

    override val gamebook = TEMPLE_OF_TERROR
}
