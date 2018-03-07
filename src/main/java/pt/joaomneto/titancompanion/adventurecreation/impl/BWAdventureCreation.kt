package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import java.io.BufferedWriter
import java.io.IOException

class BWAdventureCreation : AdventureCreation() {

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("provisions=10\n")
        bw.write("provisionsValue=4\n")
        bw.write("gold=0\n")
    }

    override fun validateCreationSpecificParameters(): String {
        return ""
    }

    override fun rollGamebookSpecificStats(view: View) {
    }
}
