package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.awf.AWFAdventureCreationSuperpowerFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

class AWFAdventureCreation : AdventureCreation(
    arrayOf(
        AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats, VitalStatisticsFragment::class),
        AdventureFragmentRunner(
            R.string.title_adventure_creation_superpower,
            AWFAdventureCreationSuperpowerFragment::class
        )
    )
) {

    var superPower: String? = null

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("heroPoints=0\n")
        bw.write("superPower=\n")
    }

    override fun validateCreationSpecificParameters(): String? {
        val sb = StringBuilder()
        if (this.superPower == null || this.superPower!!.length == 0) {
            sb.append(getString(R.string.aodSuperpower))
        }
        return sb.toString()
    }

    override fun rollGamebookSpecificStats(view: View) {}
}
