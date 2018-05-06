package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.GATES_OF_DEATH

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestGOD : TestST() {

    override val gamebook = GATES_OF_DEATH
}