package pt.joaomneto.titancompanion.adventurecreation.impl

import java.io.BufferedWriter
import java.io.IOException

class POEAdventureCreation : EOTDAdventureCreation() {

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("standardPotion=-1\n")
        bw.write("standardPotionValue=-1\n")
        bw.write("provisions=0\n")
        bw.write("provisionsValue=4\n")
        bw.write("gold=0\n")
    }
}