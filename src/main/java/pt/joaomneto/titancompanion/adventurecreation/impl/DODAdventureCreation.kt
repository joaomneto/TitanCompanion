package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import java.io.BufferedWriter
import java.io.IOException

/**
 * Created by Cristina on 28-07-2015.
 */
class DODAdventureCreation : TFODAdventureCreation() {

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        super.storeAdventureSpecificValuesInFile(bw)
        bw.write("poison=0\n")
        bw.write("medallions=\n")
    }
}
