package pt.joaomneto.titancompanion.phase1

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.DEATHTRAP_DUNGEON

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestDD : TestTFOD() {

    override val gamebook = DEATHTRAP_DUNGEON
}
