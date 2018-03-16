package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.PotionsFragment
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.pof.POFVitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class POFAdventureCreation : TFODAdventureCreation(
    arrayOf(
        AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            POFVitalStatisticsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.title_adventure_creation_potions,
            PotionsFragment::class
        )
    )
) {

    private var powerValue = -1

    private val pofVitalStatisticsFragment: POFVitalStatisticsFragment?
        get() = getFragment(POFVitalStatisticsFragment::class)

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("standardPotion=" + potion + "\n")
        bw.write("standardPotionValue=1\n")
        bw.write("provisions=0\n")
        bw.write("provisionsValue=0\n")
        bw.write("gold=0\n")
        bw.write("currentPower=" + powerValue + "\n")
        bw.write("initialPower=" + powerValue + "\n")
    }

    override fun rollGamebookSpecificStats(view: View) {
        powerValue = DiceRoller.roll2D6().sum + 6
        pofVitalStatisticsFragment?.powerValue?.text = "" + powerValue
    }

    override fun validateCreationSpecificParameters(): String? {
        val sb = StringBuilder()
        if (this.potion < 0) {
            sb.append(getString(R.string.potion))
        }
        return sb.toString()
    }
}
