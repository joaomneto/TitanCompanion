package pt.joaomneto.titancompanion.phase1

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.FREEWAY_FIGHTER

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestFF : TestST() {

    override val gamebook = FREEWAY_FIGHTER
}
