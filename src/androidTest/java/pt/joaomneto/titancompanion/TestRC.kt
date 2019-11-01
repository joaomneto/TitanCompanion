package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.ROBOT_COMMANDO

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestRC : TestST() {
    override val gamebook = ROBOT_COMMANDO
}
