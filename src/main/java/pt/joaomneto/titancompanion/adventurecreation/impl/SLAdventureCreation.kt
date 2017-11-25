package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class SLAdventureCreation : AdventureCreation() {

    var rating = 0

    init {
        AdventureCreation.fragmentConfiguration.clear()
        AdventureCreation.fragmentConfiguration.put(0, AdventureFragmentRunner(
                R.string.title_adventure_creation_vitalstats,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"))

    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("currentOxygen=10\n")
        bw.write("initialOxygen=10\n")
        bw.write("currentLasers=4\n")
        bw.write("initialLasers=4\n")
        bw.write("currentShields=12\n")
        bw.write("initialShields=12\n")
        bw.write("provisions=10\n")
        bw.write("rating=$rating\n")
        bw.write("provisionsValue=4\n")
        bw.write("gold=0\n")
    }

    override fun rollGamebookSpecificStats(view: View) {
        rating = DiceRoller.rollD6()
    }

    override fun validateCreationSpecificParameters(): String? {
        return null
    }
}
