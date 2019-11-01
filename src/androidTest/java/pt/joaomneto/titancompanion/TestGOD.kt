package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.GATES_OF_DEATH

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestGOD : TestST() {

    override val gamebook = GATES_OF_DEATH
}
