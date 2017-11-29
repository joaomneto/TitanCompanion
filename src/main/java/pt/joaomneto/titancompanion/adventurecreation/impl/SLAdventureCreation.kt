package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sl.SLVitalStatisticsFragment
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class SLAdventureCreation : AdventureCreation() {

    var rating = 0

    init {
        AdventureCreation.fragmentConfiguration.clear()
        AdventureCreation.fragmentConfiguration.put(0, AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sl.SLVitalStatisticsFragment"))

    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("currentOxygen=10\n")
        bw.write("initialOxygen=10\n")
        bw.write("currentLasers=4\n")
        bw.write("currentShields=12\n")
        bw.write("provisions=10\n")
        bw.write("rating=$rating\n")
        bw.write("provisionsValue=4\n")
        bw.write("gold=0\n")
        bw.write("starspray=true")
    }

    private fun getSLVitalStatisticsFragment(): SLVitalStatisticsFragment {
        return getFragments()[0] as SLVitalStatisticsFragment
    }

    override fun rollGamebookSpecificStats(view: View) {
        rating = DiceRoller.rollD6()
        getSLVitalStatisticsFragment().getRatingValue()?.text = rating.toString()
    }

    override fun validateCreationSpecificParameters(): String? {
        return null
    }
}
