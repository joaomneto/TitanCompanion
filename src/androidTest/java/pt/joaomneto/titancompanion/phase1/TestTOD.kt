package pt.joaomneto.titancompanion.phase1

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.TALISMAN_OF_DEATH

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestTOD : TestTFOD() {

    override val gamebook = TALISMAN_OF_DEATH
}
