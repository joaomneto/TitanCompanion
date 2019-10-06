package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import java.io.BufferedWriter
import java.io.IOException
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation

/**
 * Created by Cristina on 28-07-2015.
 */
class COHAdventureCreation : AdventureCreation() {

    override fun rollGamebookSpecificStats(view: View) {
    }

    override fun validateCreationSpecificParameters(): String? {
        return null
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("gold=0\n")
    }
}
