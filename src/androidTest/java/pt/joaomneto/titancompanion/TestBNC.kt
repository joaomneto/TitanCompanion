package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.BENEATH_NIGHTMARE_CASTLE

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestBNC : TestST() {

    override val gamebook = BENEATH_NIGHTMARE_CASTLE
}
