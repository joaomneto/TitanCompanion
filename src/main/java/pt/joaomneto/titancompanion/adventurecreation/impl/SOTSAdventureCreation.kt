package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sots.SOTSAdventureCreationSkillFragment
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sots.SOTSMartialArt
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

class SOTSAdventureCreation : AdventureCreation(
    arrayOf(
        AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats, VitalStatisticsFragment::class),
        AdventureFragmentRunner(
            R.string.title_adventure_creation_skill,
            SOTSAdventureCreationSkillFragment::class
        )
    )
) {

    private var martialArt: SOTSMartialArt? = null

    override fun rollGamebookSpecificStats(view: View) {}

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("honour=3\n")
        bw.write("skill=" + martialArt!!.name + "\n")
        bw.write("provisions=10\n")
        bw.write("provisionsValue=4\n")
        bw.write("gold=0\n")
        if (SOTSMartialArt.KYUJUTSU == martialArt) {
            bw.write("willowLeafArrows=3\n")
            bw.write("bowelRakerArrows=3\n")
            bw.write("armourPiercerArrows=3\n")
            bw.write("hummingBulbArrows=3\n")
        }
    }

    fun setSkill(martialArt: SOTSMartialArt) {
        this.martialArt = martialArt
    }

    override fun validateCreationSpecificParameters(): String? {
        val sb = StringBuilder()
        if (this.martialArt == null) {
            sb.append("Martial Art")
        }
        return sb.toString()
    }
}
