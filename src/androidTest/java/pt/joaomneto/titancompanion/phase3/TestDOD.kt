package pt.joaomneto.titancompanion.phase3

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.TestTFOD
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.DAGGERS_OF_DARKNESS

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestDOD : TestTFOD() {

    override val gamebook = DAGGERS_OF_DARKNESS
}