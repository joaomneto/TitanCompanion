package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.TRIAL_OF_CHAMPIONS

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestTOC : TestST() {

    override val gamebook = TRIAL_OF_CHAMPIONS
}
