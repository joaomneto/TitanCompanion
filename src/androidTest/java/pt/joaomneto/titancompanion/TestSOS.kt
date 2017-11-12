package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CITY_OF_THIEVES

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSOS : TestTFOD() {

    override fun getGamebook(): FightingFantasyGamebook {
        return CITY_OF_THIEVES
    }

}