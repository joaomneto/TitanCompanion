package pt.joaomneto.titancompanion.adventurecreation.impl

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.PotionsFragment
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

open class TWOFMAdventureCreation(override val fragmentConfiguration: Array<AdventureFragmentRunner> = DEFAULT_FRAGMENTS) : AdventureCreation(
    fragmentConfiguration
) {

    companion object {
        val DEFAULT_FRAGMENTS = arrayOf(
            AdventureFragmentRunner(
                R.string.title_adventure_creation_vitalstats,
                VitalStatisticsFragment::class
            ),
            AdventureFragmentRunner(
                R.string.title_adventure_creation_potions,
                PotionsFragment::class
            )
        )
    }

    var potion = -1
    var potionDoses = -1

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("standardPotion=" + potion + "\n")
        bw.write("standardPotionValue=" + potionDoses + "\n")
        bw.write("provisions=10\n")
        bw.write("provisionsValue=4\n")
        bw.write("gold=0\n")
    }

    override fun validateCreationSpecificParameters(): String? {
        val sb = StringBuilder()
        if (this.potion < 0) {
            sb.append(getString(R.string.potion))
        }
        return sb.toString()
    }
}
