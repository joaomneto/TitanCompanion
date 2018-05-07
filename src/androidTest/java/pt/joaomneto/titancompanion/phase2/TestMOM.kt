package pt.joaomneto.titancompanion.phase2

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.MASKS_OF_MAYHEM
import pt.joaomneto.titancompanion.phase1.TestTFOD

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestMOM : TestTFOD() {

    override val gamebook = MASKS_OF_MAYHEM
}
