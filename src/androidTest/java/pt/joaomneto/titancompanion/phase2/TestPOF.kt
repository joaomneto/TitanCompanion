package pt.joaomneto.titancompanion.phase2

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.PHANTOMS_OF_FEAR
import pt.joaomneto.titancompanion.phase1.TestTFOD

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestPOF : TestTFOD() {

    override val gamebook = PHANTOMS_OF_FEAR
}
