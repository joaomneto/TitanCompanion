package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.REBEL_PLANET

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestRP : TestST() {

    override val gamebook = REBEL_PLANET


}