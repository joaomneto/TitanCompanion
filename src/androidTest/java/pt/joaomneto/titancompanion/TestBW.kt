package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestBW : TestST() {

    override fun getGamebook(): FightingFantasyGamebook {
        return FightingFantasyGamebook.EYE_OF_THE_DRAGON
    }


}