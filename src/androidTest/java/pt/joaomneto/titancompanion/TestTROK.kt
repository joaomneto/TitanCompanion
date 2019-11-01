package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_RINGS_OF_KETHER

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestTROK : TestST() {

    override val gamebook = THE_RINGS_OF_KETHER
}
