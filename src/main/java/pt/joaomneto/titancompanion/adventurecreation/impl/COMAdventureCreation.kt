package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

class COMAdventureCreation : TWOFMAdventureCreation(
    arrayOf(
        AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            VitalStatisticsFragment::class
        )
    )
) {

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        super.storeAdventureSpecificValuesInFile(bw)
        bw.write("tabasha=9\n")
        bw.write("tabashaSpecialSkill=9\n")
        bw.write("kuddamKilled=\n")
        bw.write("gold=0\n")
        bw.write("fuel=0\n")
        bw.write("provisions=0\n")
        bw.write("cyphers=\n")
    }

    override fun rollGamebookSpecificStats(view: View) {}

    override fun validateCreationSpecificParameters(): String {
        return NO_PARAMETERS_TO_VALIDATE
    }
}