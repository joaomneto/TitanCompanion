package pt.joaomneto.titancompanion.phase1

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.FREEWAY_FIGHTER

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestFF : TestST() {

    override val gamebook = FREEWAY_FIGHTER
}
