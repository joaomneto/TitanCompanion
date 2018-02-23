package pt.joaomneto.titancompanion.adventurecreation.impl

import java.io.BufferedWriter
import java.io.IOException

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import android.view.View

class SSAdventureCreation : AdventureCreation() {
    init {
        fragmentConfiguration.clear()
        fragmentConfiguration.put(0, AdventureFragmentRunner(
                R.string.title_adventure_creation_vitalstats,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"))

    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("spells=\n")
        bw.write("clearings=\n")
        bw.write("gold=0")
    }

    override fun rollGamebookSpecificStats(view: View) {}


    override fun validateCreationSpecificParameters(): String {
        return AdventureCreation.NO_PARAMETERS_TO_VALIDATE
    }

}
