package pt.joaomneto.titancompanion.phase3

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.TestST
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SPELLBREAKER

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestS : TestST() {

    override val gamebook = SPELLBREAKER
}