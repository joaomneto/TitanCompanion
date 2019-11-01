package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.PHANTOMS_OF_FEAR

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestPOF : TestTFOD() {

    override val gamebook = PHANTOMS_OF_FEAR
}
