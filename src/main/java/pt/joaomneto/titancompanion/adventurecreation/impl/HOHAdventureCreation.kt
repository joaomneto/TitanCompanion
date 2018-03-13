package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.hoh.HOHVitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class HOHAdventureCreation : AdventureCreation(
    arrayOf(
        AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            HOHVitalStatisticsFragment::class
        )
    )
) {

    var fearValue = -1

    private val hohVitalStatisticsFragment: HOHVitalStatisticsFragment?
        get() = getFragment(HOHVitalStatisticsFragment::class)

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("currentFear=$fearValue\n")
        bw.write("maximumFear=$fearValue\n")
    }

    override fun rollGamebookSpecificStats(view: View) {
        fearValue = DiceRoller.rollD6() + 6
        hohVitalStatisticsFragment?.fearValue?.text = fearValue.toString()
    }

    override fun validateCreationSpecificParameters(): String? {
        val sb = StringBuilder()
        if (this.fearValue < 0) {
            sb.append(R.string.fear2)
        }
        return sb.toString()
    }
}
