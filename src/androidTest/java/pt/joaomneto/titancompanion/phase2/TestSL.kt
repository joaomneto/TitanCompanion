package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SKY_LORD

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSL : TestST() {

    override val gamebook = SKY_LORD
}