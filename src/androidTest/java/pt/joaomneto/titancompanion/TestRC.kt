package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.ROBOT_COMMANDO

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestRC : TestST() {

    override val gamebook = ROBOT_COMMANDO


}