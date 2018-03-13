package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tcoc.TCOCAdventureCreationSpellsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap

class TCOCAdventureCreation : AdventureCreation(
    arrayOf(
        AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            VitalStatisticsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.spells,
            TCOCAdventureCreationSpellsFragment::class
        )
    )
) {

    var spells: MutableMap<String, Int> = HashMap()
    private var internalSpellList: MutableList<String> = ArrayList()
    var spellList: MutableList<String> = ArrayList()
        get() {
            spells.keys
                .filterNot { this.internalSpellList.contains(it) }
                .forEach { this.internalSpellList.add(it) }

            this.internalSpellList.sort()
            return this.internalSpellList
        }

    var spellValue = -1

    private val tcocSpellsFragment: TCOCAdventureCreationSpellsFragment?
        get() = getFragment(TCOCAdventureCreationSpellsFragment::class)

    val spellListSize: Int
        get() {
            var size = 0
            spellList
                .asSequence()
                .map { spells[it] }
                .forEach {
                    size += it ?: 1
                }
            return size
        }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        var spellsS = ""

        if (!spells.isEmpty()) {
            for (spell in spellList) {
                spellsS += spell + "§" + spells[spell] + "#"
            }
            spellsS = spellsS.substring(0, spellsS.length - 1)
        }

        bw.write("spellValue=$spellValue\n")
        bw.write("spells=$spellsS\n")
        bw.write("gold=0\n")
    }

    override fun validateCreationSpecificParameters(): String? {
        val sb = StringBuilder()
        var error = false
        if (this.spellValue < 0) {
            sb.append(getString(R.string.spellCount))
            error = true
        }
        sb.append(if (error) "; " else "")
        if (this.spells.isEmpty()) {
            sb.append(getString(R.string.chosenSpells))
        }

        return sb.toString()
    }

    override fun rollGamebookSpecificStats(view: View) {
        spellValue = DiceRoller.roll2D6().sum + 6
        tcocSpellsFragment?.spellScoreValue?.text = "" + spellValue
    }

    fun addSpell(spell: String) {
        if (!spells.containsKey(spell)) {
            spells[spell] = 0
        }
        spells[spell] = spells[spell]!! + 1
    }

    fun removeSpell(position: Int) {
        val spell = spellList[position]
        val value = spells[spell]!! - 1
        if (value == 0) {
            spells.remove(spell)
            spellList.removeAt(position)
        } else {
            spells[spell] = value
        }
    }
}
