package pt.joaomneto.titancompanion.adventurecreation.impl

import java.io.BufferedWriter
import java.io.IOException

class TPOPAdventureCreation : TFODAdventureCreation() {

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        super.storeAdventureSpecificValuesInFile(bw)

        bw.write("copper=0\n")
    }
}
