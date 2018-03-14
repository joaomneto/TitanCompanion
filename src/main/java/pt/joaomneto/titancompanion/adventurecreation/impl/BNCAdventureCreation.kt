package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.bnc.BNCVitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class BNCAdventureCreation : AdventureCreation(
    arrayOf(
        AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            BNCVitalStatisticsFragment::class
        )
    )
) {

    private var willpowerValue = -1

    private val bncVitalStatisticsFragment: BNCVitalStatisticsFragment?
        get() = getFragment(BNCVitalStatisticsFragment::class)

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("currentWillpower=" + willpowerValue + "\n")
        bw.write("initialWillpower=" + willpowerValue + "\n")
        bw.write("provisions=0\n")
        bw.write("provisionsValue=4\n")
        bw.write("gold=0\n")
    }

    override fun rollGamebookSpecificStats(view: View) {
        willpowerValue = DiceRoller.rollD6() + 6
        bncVitalStatisticsFragment?.willpowerValue?.text = "" + willpowerValue
    }

    override fun validateCreationSpecificParameters(): String? {
        val sb = StringBuilder()
        if (this.willpowerValue < 0) {
            sb.append(getString(R.string.bncWillpower))
        }
        return sb.toString()
    }
}
