package pt.joaomneto.titancompanion.phase1

import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.DEATHTRAP_DUNGEON

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestDD : TestTFOD() {

    override val gamebook = DEATHTRAP_DUNGEON
}
