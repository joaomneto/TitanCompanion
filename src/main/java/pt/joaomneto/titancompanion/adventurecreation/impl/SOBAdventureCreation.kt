package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sob.SOBVitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class SOBAdventureCreation : AdventureCreation(
    arrayOf(
        AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            SOBVitalStatisticsFragment::class
        )
    )
) {

    private var currentCrewStrength = -1
    private var currentCrewStrike = -1

    private val sobVitalStatisticsFragment: SOBVitalStatisticsFragment?
        get() = getFragment(SOBVitalStatisticsFragment::class)

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("currentCrewStrength=" + currentCrewStrength + "\n")
        bw.write("currentCrewStrike=" + currentCrewStrike + "\n")
        bw.write("initialCrewStrength=" + currentCrewStrength + "\n")
        bw.write("initialCrewStrike=" + currentCrewStrike + "\n")
        bw.write("log=0\n")
        bw.write("gold=20\n")
        bw.write("booty=\n")
    }

    override fun rollGamebookSpecificStats(view: View) {
        currentCrewStrike = DiceRoller.rollD6() + 6
        currentCrewStrength = DiceRoller.roll2D6().sum + 6
        sobVitalStatisticsFragment?.crewStrikeValue?.text = "" + currentCrewStrike
        sobVitalStatisticsFragment?.crewStrengthValue?.text = "" + currentCrewStrength
    }

    override fun validateCreationSpecificParameters(): String? {
        val sb = StringBuilder()
        if (this.currentCrewStrike < 0) {
            sb.append(getString(R.string.crewParamsMandatory))
        }
        return sb.toString()
    }
}
