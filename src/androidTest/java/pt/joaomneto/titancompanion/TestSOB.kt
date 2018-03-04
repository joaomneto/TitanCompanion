package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SEAS_OF_BLOOD

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSOB : TestST() {

    override val gamebook = SEAS_OF_BLOOD


}