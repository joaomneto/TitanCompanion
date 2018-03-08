package pt.joaomneto.titancompanion.adventurecreation.impl

import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import java.io.BufferedWriter

class COTSAdventureCreation : AdventureCreation() {
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        super.storeAdventureSpecificValuesInFile(bw)
        bw.write("provisions=0\n")
        bw.write("provisionsValue=4\n")
    }
}
