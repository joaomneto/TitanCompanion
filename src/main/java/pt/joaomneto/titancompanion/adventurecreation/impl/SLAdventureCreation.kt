package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sl.SLVitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class SLAdventureCreation : AdventureCreation(
    arrayOf(
        AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            SLVitalStatisticsFragment::class
        )
    )
) {

    var rating = 0

    private val slVitalStatisticsFragment: SLVitalStatisticsFragment?
        get() = getFragment(SLVitalStatisticsFragment::class)

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("oxygen=10\n")
        bw.write("currentLasers=4\n")
        bw.write("currentShields=12\n")
        bw.write("provisions=10\n")
        bw.write("rating=$rating\n")
        bw.write("provisionsValue=4\n")
        bw.write("gold=0\n")
        bw.write("starspray=true")
    }

    override fun rollGamebookSpecificStats(view: View) {
        rating = DiceRoller.rollD6()
        slVitalStatisticsFragment?.getRatingValue()?.text = rating.toString()
    }

    override fun validateCreationSpecificParameters(): String? {
        return null
    }
}
