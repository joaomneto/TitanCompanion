package pt.joaomneto.titancompanion.phase3

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import pt.joaomneto.titancompanion.phase1.TestST

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestPOE : TestST() {

    override val gamebook = FightingFantasyGamebook.PORTAL_OF_EVIL
}
