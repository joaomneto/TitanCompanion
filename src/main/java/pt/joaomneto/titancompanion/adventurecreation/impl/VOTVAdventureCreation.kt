package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import java.io.BufferedWriter
import java.io.IOException
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.bnc.VOTVVitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller

class VOTVAdventureCreation : AdventureCreation(
    arrayOf(
        AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            VOTVVitalStatisticsFragment::class
        )
    )
) {

    private var faithValue = -1

    private val bncVitalStatisticsFragment: VOTVVitalStatisticsFragment?
        get() = getFragment(VOTVVitalStatisticsFragment::class)

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("currentWillpower=$faithValue\n")
        bw.write("initialWillpower=$faithValue\n")
        bw.write("provisions=0\n")
        bw.write("provisionsValue=10\n")
        bw.write("gold=0\n")
    }

    override fun rollGamebookSpecificStats(view: View) {
        faithValue = DiceRoller.rollD6() + 6
        bncVitalStatisticsFragment?.willpowerValue?.text = "" + faithValue
    }

    override fun validateCreationSpecificParameters(): String? {
        val sb = StringBuilder()
        if (this.faithValue < 0) {
            sb.append(getString(R.string.votvFaith))
        }
        return sb.toString()
    }
}
