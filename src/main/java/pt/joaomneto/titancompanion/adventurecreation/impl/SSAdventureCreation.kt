package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import android.support.v4.app.Fragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import java.io.BufferedWriter
import java.io.IOException

class SSAdventureCreation : AdventureCreation() {

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("spells=\n")
    }
}
