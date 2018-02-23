package pt.joaomneto.titancompanion.adventurecreation.impl

import java.io.BufferedWriter
import java.io.IOException

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sots.SOTSMartialArt

import android.view.View

class SOTSAdventureCreation : AdventureCreation() {

    var martialArt: SOTSMartialArt? = null
        internal set

    init {
        AdventureCreation.Companion.fragmentConfiguration.clear()
        AdventureCreation.Companion.fragmentConfiguration.put(0, AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats, "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"))
        AdventureCreation.Companion.fragmentConfiguration.put(1, AdventureFragmentRunner(R.string.title_adventure_creation_skill,
            "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sots.SOTSAdventureCreationSkillFragment"))
    }

    override fun rollGamebookSpecificStats(view: View) { }

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
