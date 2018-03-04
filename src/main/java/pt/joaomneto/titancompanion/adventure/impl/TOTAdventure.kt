package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import android.support.v4.app.Fragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.SpellAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.*
import pt.joaomneto.titancompanion.adventure.impl.fragments.tot.TOTSpell
import pt.joaomneto.titancompanion.adventure.impl.util.Spell
import java.io.BufferedWriter
import java.io.IOException
import java.util.*

class TOTAdventure : SpellAdventure<TOTSpell>(
        arrayOf(
                AdventureFragmentRunner(R.string.vitalStats,
                        AdventureVitalStatsFragment::class),
                AdventureFragmentRunner(R.string.fights,
                        AdventureCombatFragment::class),
                AdventureFragmentRunner(R.string.spells,
                        AdventureSpellsFragment::class),
                AdventureFragmentRunner(R.string.goldEquipment,
                        AdventureEquipmentFragment::class),
                AdventureFragmentRunner(R.string.notes,
                        AdventureNotesFragment::class))) {

    var spells: List<Spell> = ArrayList()

    override val spellList: List<TOTSpell> = TOTSpell.values().toList()

    override val isSpellSingleUse: Boolean
        get() = false


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

        bw.write("spells=" + arrayToStringSpells(chosenSpells) + "\n")
        bw.write("gold=" + gold + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        gold = Integer.valueOf(savedGame.getProperty("gold"))

        chosenSpells = stringToArraySpells(String(savedGame.getProperty("spells").toByteArray(java.nio.charset.Charset.forName("ISO-8859-1"))), TOTSpell::class.java)

    }

    companion object {

        protected val FRAGMENT_SPELLS = 2
        protected val FRAGMENT_EQUIPMENT = 3
        protected val FRAGMENT_NOTES = 4
    }

}
