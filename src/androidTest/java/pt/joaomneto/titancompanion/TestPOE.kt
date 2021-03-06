package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestPOE : TestST() {

    override val gamebook = FightingFantasyGamebook.PORTAL_OF_EVIL
}
