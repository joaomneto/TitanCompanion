package pt.joaomneto.titancompanion.phase2

import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.STAR_STRIDER
import pt.joaomneto.titancompanion.phase1.TestST

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSTRIDER : TestST() {

    override val gamebook = STAR_STRIDER
}
