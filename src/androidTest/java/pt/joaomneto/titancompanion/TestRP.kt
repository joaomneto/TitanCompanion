package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.REBEL_PLANET

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestRP : TestST() {

    override val gamebook = REBEL_PLANET
}
