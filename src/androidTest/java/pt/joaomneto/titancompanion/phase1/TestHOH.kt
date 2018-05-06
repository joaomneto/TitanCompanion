package pt.joaomneto.titancompanion.phase1

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.HOUSE_OF_HELL

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestHOH : TestST() {

    override val gamebook = HOUSE_OF_HELL
}
