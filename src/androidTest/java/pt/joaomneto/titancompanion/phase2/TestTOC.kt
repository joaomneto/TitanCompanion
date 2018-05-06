package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.TRIAL_OF_CHAMPIONS

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestTOC : TestST() {

    override val gamebook = TRIAL_OF_CHAMPIONS
}