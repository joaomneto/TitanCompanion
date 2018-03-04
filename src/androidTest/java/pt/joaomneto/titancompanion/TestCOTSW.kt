package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CAVERNS_OF_THE_SNOW_WITCH

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestCOTSW : TestTFOD() {

    override val gamebook = CAVERNS_OF_THE_SNOW_WITCH

}