package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.ISLAND_OF_THE_LIZARD_KING

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestIOTLK : TestTFOD() {

    override val gamebook = ISLAND_OF_THE_LIZARD_KING
}
