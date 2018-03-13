package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.strider.STRIDERVitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class STRIDERAdventureCreation : AdventureCreation(
    arrayOf(
        AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            STRIDERVitalStatisticsFragment::class
        )
    )
) {

    var fearValue = -1

    private val striderVitalStatisticsFragment: STRIDERVitalStatisticsFragment?
        get() = getFragment(STRIDERVitalStatisticsFragment::class)

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("fear=" + fearValue + "\n")
        bw.write("time=" + 0 + "\n")
        bw.write("oxygen=" + 0 + "\n")
    }

    override fun rollGamebookSpecificStats(view: View) {
        fearValue = DiceRoller.rollD6() + 6
        striderVitalStatisticsFragment?.fearValue?.text = "" + fearValue
    }
}
