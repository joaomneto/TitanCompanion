package pt.joaomneto.titancompanion.adventurecreation.impl

import java.io.BufferedWriter
import java.io.IOException
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation

/**
 * Created by Cristina on 28-07-2015.
 */
class RCAdventureCreation : AdventureCreation() {

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("gold=0\n")
        bw.write("robots=\n")
        bw.write("provisions=5\n")
        bw.write("provisionsValue=1\n")
    }
}
