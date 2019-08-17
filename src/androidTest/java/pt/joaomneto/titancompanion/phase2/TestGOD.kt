package pt.joaomneto.titancompanion

import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.GATES_OF_DEATH
import pt.joaomneto.titancompanion.phase1.TestST

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestGOD : TestST() {

    override val gamebook = GATES_OF_DEATH
}
