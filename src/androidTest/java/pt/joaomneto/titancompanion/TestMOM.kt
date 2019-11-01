package pt.joaomneto.titancompanion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.MASKS_OF_MAYHEM

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestMOM : TestTFOD() {

    override val gamebook = MASKS_OF_MAYHEM
}
