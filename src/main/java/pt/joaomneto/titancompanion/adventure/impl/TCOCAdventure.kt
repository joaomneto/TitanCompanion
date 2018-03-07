package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.tcoc.TCOCAdventureSpellsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.HashMap

class TCOCAdventure : Adventure(
    arrayOf(
        AdventureFragmentRunner(
            R.string.vitalStats,
            AdventureVitalStatsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.fights,
            AdventureCombatFragment::class
        ),
        AdventureFragmentRunner(
            R.string.spells,
            TCOCAdventureSpellsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.goldEquipment,
            AdventureEquipmentFragment::class
        ),
        AdventureFragmentRunner(
            R.string.notes,
            AdventureNotesFragment::class
        )
    )
) {

    internal var spells: MutableMap<String, Int> = HashMap()
    internal var spellList: MutableList<String> = ArrayList()
    internal var spellValue: Int? = null

    val spellListSize: Int
        get() {
            var size = 0
            for (spell in getSpellList()) {
                val value = spells[spell]
                if (value != null) {
                    size += value
                } else {
                    size += 1
                }
            }
            return size
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.adventure, menu)
        return true
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        var spellsS = ""

        if (!spells.isEmpty()) {
            for (spell in getSpellList()) {
                spellsS += spell + "§" + spells[spell] + "#"
            }
            spellsS = spellsS.substring(0, spellsS.length - 1)
        }

        bw.write("spells=" + spellsS + "\n")
        bw.write("spellValue=" + spellValue + "\n")
        bw.write("gold=" + gold + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        gold = Integer.valueOf(savedGame.getProperty("gold"))
        spellValue = Integer.valueOf(savedGame.getProperty("spellValue"))
        val spellsS = String(
            savedGame.getProperty("spells")
                .toByteArray(java.nio.charset.Charset.forName("UTF-8"))
        )

        spells = HashMap()
        val list = Arrays.asList(*spellsS.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        for (string in list) {
            if (!string.isEmpty()) {
                val split = string.split("§".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                spells.put(split[0], Integer.parseInt(split[1]))
            }
        }
    }

    @Synchronized
    fun getSpellList(): MutableList<String> {
        for (spell in spells.keys) {
            if (!spellList.contains(spell)) {
                spellList.add(spell)
            }
        }

        Collections.sort(spellList)
        return spellList
    }

    fun removeSpell(position: Int) {
        val spell = getSpellList()[position]
        val value = spells[spell]!! - 1
        if (value == 0) {
            spells.remove(spell)
            getSpellList().remove(spell)
        } else {
            spells.put(spell, value)
        }
    }

    fun getSpells(): Map<String, Int> {
        return spells
    }

    companion object {

        protected val FRAGMENT_SPELLS = 2
        protected val FRAGMENT_EQUIPMENT = 3
        protected val FRAGMENT_NOTES = 4
    }
}
