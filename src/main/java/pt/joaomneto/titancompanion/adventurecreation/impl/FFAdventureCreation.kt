package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.ff.FFVitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class FFAdventureCreation : AdventureCreation(
    arrayOf(
        AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            FFVitalStatisticsFragment::class
        )
    )
) {

    private var currentFirepower = -1
    private var currentArmour = -1

    private val ffVitalStatsFragment: FFVitalStatisticsFragment?
        get() = getFragment(FFVitalStatisticsFragment::class)

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("currentFirepower=" + currentFirepower + "\n")
        bw.write("currentArmour=" + currentArmour + "\n")
        bw.write("initialFirepower=" + currentFirepower + "\n")
        bw.write("initialArmour=" + currentArmour + "\n")
        bw.write("rockets=4\n")
        bw.write("ironSpikes=3\n")
        bw.write("oilCannisters=2\n")
        bw.write("provisions=10\n")
        bw.write("provisionsValue=4\n")
        bw.write("spareWheels=2\n")
        bw.write("gold=200\n")
        bw.write("carEnhancements=\n")
    }

    override fun validateCreationSpecificParameters(): String? {
        val sb = StringBuilder()
        if (this.currentFirepower < 0) {
            sb.append(getString(R.string.ffFirepowerAndArmor))
        }
        return sb.toString()
    }

    override fun rollGamebookSpecificStats(view: View) {
        currentFirepower = DiceRoller.rollD6() + 6
        currentArmour = DiceRoller.roll2D6().sum + 24
        stamina = DiceRoller.roll2D6().sum + 24
        ffVitalStatsFragment?.firepowerValue?.text = currentFirepower.toString()
        ffVitalStatsFragment?.armorValue?.text = currentArmour.toString()
    }
}
