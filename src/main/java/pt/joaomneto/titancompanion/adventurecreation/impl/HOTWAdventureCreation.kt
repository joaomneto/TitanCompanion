package pt.joaomneto.titancompanion.adventurecreation.impl

import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import java.io.BufferedWriter
import java.io.IOException

/**
 * Created by Cristina on 28-07-2015.
 */
class HOTWAdventureCreation : AdventureCreation() {

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("gold=0\n")
        bw.write("provisionsValue=4\n")
        bw.write("provisions=10\n")
        bw.write("notes=\n")
        bw.write("change=0\n")
    }
}
