package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import kotlinx.android.synthetic.main.fragment_38votv_adventurecreation_vital_statistics.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.votv.VOTVVitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class VOTVAdventureCreation : AdventureCreation(
    arrayOf(
        AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            VOTVVitalStatisticsFragment::class
        )
    )
) {

    private var faithValueCount = -1

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("currentFaith=$faithValueCount\n")
        bw.write("initialFaith=$faithValueCount\n")
        bw.write("spells=\n")
        bw.write("afflictions=\n")
        bw.write("provisions=0\n")
        bw.write("provisionsValue=10\n")
        bw.write("gold=0\n")
    }

    override fun rollGamebookSpecificStats(view: View) {
        faithValueCount = DiceRoller.rollD6() + 3
        faithValue?.text = faithValueCount.toString()
    }

    override fun validateCreationSpecificParameters(): String? {
        val sb = StringBuilder()
        if (this.faithValueCount < 0) {
            sb.append(getString(R.string.votvFaith))
        }
        return sb.toString()
    }
}
