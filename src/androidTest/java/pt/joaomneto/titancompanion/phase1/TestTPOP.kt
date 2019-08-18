package pt.joaomneto.titancompanion.phase3

import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import pt.joaomneto.titancompanion.phase1.TestTFOD

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestTPOP : TestTFOD() {

    override val gamebook = FightingFantasyGamebook.THE_PORT_OF_PERIL
}
