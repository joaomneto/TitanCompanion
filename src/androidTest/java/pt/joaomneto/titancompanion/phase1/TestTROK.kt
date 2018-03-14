package pt.joaomneto.titancompanion.phase1

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_RINGS_OF_KETHER
import pt.joaomneto.titancompanion.phase1.TestST

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestTROK : TestST() {

    override val gamebook = THE_RINGS_OF_KETHER
}