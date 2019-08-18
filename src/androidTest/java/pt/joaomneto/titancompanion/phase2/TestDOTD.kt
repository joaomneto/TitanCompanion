package pt.joaomneto.titancompanion.phase2

import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.DEMONS_OF_THE_DEEP
import pt.joaomneto.titancompanion.phase1.TestTFOD

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestDOTD : TestTFOD() {

    override val gamebook = DEMONS_OF_THE_DEEP
}
