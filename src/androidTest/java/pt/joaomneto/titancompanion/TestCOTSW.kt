package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CAVERNS_OF_THE_SNOW_WITCH

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestCOTSW : TestTFOD() {

    override val gamebook = CAVERNS_OF_THE_SNOW_WITCH
}
