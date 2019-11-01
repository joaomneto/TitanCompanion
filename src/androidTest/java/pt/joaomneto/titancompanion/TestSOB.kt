package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SEAS_OF_BLOOD

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSOB : TestST() {

    override val gamebook = SEAS_OF_BLOOD
}
