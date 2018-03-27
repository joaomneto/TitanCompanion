package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import kotlinx.android.synthetic.main.fragment_53s_adventurecreation_vital_statistics.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.s.SVitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class SAdventureCreation : AdventureCreation(
        arrayOf(
                AdventureFragmentRunner(
                        R.string.title_adventure_creation_vitalstats,
                        SVitalStatisticsFragment::class
                )
        )
) {

    private var gold = -1

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("currentFaith=1\n")
        bw.write("currentInfection=0\n")
        bw.write("provisions=5\n")
        bw.write("provisionsValue=4\n")
        bw.write("gold=$gold\n")
    }

    override fun rollGamebookSpecificStats(view: View) {
        gold = DiceRoller.rollD6() + 4
        goldValue.text = "$gold"
    }
}
