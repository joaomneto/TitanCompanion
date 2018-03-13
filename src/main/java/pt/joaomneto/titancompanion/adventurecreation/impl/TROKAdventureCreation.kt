package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.trok.TROKVitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class TROKAdventureCreation : AdventureCreation(
    arrayOf(
        AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            TROKVitalStatisticsFragment::class
        )
    )
) {

    private var currentWeapons = -1
    private var currentShields = -1

    private val trokVitalStatisticsFragment: TROKVitalStatisticsFragment?
        get() = getFragment(TROKVitalStatisticsFragment::class)

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("currentWeapons=" + currentWeapons + "\n")
        bw.write("currentShields=" + currentShields + "\n")
        bw.write("initialWeapons=" + currentWeapons + "\n")
        bw.write("initialShields=" + currentShields + "\n")
        bw.write("missiles=2\n")
        bw.write("provisions=4\n")
        bw.write("provisionsValue=6\n")
        bw.write("gold=5000\n")
    }

    override fun validateCreationSpecificParameters(): String? {
        val sb = StringBuilder()
        if (this.currentWeapons < 0) {
            sb.append(getString(R.string.starshipStatsMandatory))
        }
        return sb.toString()
    }

    override fun rollGamebookSpecificStats(view: View) {
        currentWeapons = DiceRoller.rollD6() + 6
        currentShields = DiceRoller.rollD6()
        trokVitalStatisticsFragment?.weaponsValue?.text = "" + currentWeapons
        trokVitalStatisticsFragment?.shieldsValue?.text = "" + currentShields
    }
}
