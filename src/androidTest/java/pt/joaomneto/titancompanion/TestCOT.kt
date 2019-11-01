package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CITY_OF_THIEVES

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestCOT : TestTFOD() {

    override val gamebook = CITY_OF_THIEVES
}
