package pt.joaomneto.titancompanion.phase2

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CHASMS_OF_MALICE
import pt.joaomneto.titancompanion.phase1.TestST

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestCOM : TestST() {

    override val gamebook = CHASMS_OF_MALICE
}
