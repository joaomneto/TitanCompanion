package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SPELLBREAKER

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestS : TestST() {

    override val gamebook = SPELLBREAKER
}
