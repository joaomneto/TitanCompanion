package pt.joaomneto.titancompanion.phase2

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CAVERNS_OF_THE_SNOW_WITCH
import pt.joaomneto.titancompanion.phase1.TestTFOD

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestCOTSW : TestTFOD() {

    override val gamebook = CAVERNS_OF_THE_SNOW_WITCH
}
