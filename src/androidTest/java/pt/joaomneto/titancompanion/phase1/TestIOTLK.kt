package pt.joaomneto.titancompanion.phase1

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.ISLAND_OF_THE_LIZARD_KING

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestIOTLK : TestTFOD() {

    override val gamebook = ISLAND_OF_THE_LIZARD_KING
}
