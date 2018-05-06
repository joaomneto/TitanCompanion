package pt.joaomneto.titancompanion.phase3

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.TestST
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.ARMIES_OF_DEATH

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestAOD : TestST() {
    override val gamebook = ARMIES_OF_DEATH
}