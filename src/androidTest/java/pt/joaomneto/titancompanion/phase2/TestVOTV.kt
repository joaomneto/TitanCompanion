package pt.joaomneto.titancompanion.phase2

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.VOTVAdventure
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.BENEATH_NIGHTMARE_CASTLE
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.VAULT_OF_THE_VAMPIRE
import pt.joaomneto.titancompanion.phase1.TestST

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestVOTV : TestST() {
    override val gamebook = VAULT_OF_THE_VAMPIRE

    override fun testVitalStatistics(adventure: Adventure) {
        super.testVitalStatistics(adventure)
        testFaithStat(adventure as VOTVAdventure)
    }

    private fun testFaithStat(adventure: VOTVAdventure) {
        testIncrementalStat(
            adventure = adventure,
            minusButtonId = R.id.minusFaithButton,
            plusButtonId = R.id.plusFaithButton,
            valueId = R.id.statsFaithValue,
            adventureField = VOTVAdventure::currentFaith,
            maxValueEditable = false
        )
    }
}
