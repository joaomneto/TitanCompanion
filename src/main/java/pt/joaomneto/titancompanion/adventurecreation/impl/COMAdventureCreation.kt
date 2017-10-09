package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import java.io.BufferedWriter
import java.io.IOException

class COMAdventureCreation : TWOFMAdventureCreation() {

    init {

        fragmentConfiguration.put(0, Adventure.AdventureFragmentRunner(
                R.string.title_adventure_creation_vitalstats,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"))

    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("tabasha=0\n")
        bw.write("kuddamKilled=\n")
        bw.write("gold=0\n")
        bw.write("fuel=0\n")
        bw.write("cyphers=\n")

    }

    override fun rollGamebookSpecificStats(view: View) {}


    override fun validateCreationSpecificParameters(): String {
        return NO_PARAMETERS_TO_VALIDATE
    }

}