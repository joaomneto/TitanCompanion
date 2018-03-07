package pt.joaomneto.titancompanion.adventurecreation.impl

import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import java.io.BufferedWriter
import java.io.IOException

class RPAdventureCreation : AdventureCreation() {

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("gold=2000\n")
    }
}
