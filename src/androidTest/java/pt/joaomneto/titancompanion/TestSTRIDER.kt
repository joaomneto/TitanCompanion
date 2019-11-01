package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.STAR_STRIDER

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSTRIDER : TestST() {

    override val gamebook = STAR_STRIDER
}
