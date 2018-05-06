package pt.joaomneto.titancompanion.phase3

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.TestTFOD
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestTPOP : TestTFOD() {

    override val gamebook = FightingFantasyGamebook.THE_PORT_OF_PERIL
}