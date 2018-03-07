package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import java.io.BufferedWriter
import java.io.IOException

class AODAdventureCreation : AdventureCreation() {

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("gold=700\n")
        bw.write("soldiers=Warriors§100#Dwarves§50#Elves§50#Knights§20\n")
    }

    override fun rollGamebookSpecificStats(view: View) {}

    override fun validateCreationSpecificParameters(): String? {
        return AdventureCreation.Companion.NO_PARAMETERS_TO_VALIDATE
    }
}
