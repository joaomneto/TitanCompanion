package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.STAR_STRIDER

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSTRIDER : TestST() {

    override val gamebook = STAR_STRIDER


}