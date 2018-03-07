package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.SpellAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureSpellsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.ss.SSAdventureMapFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.ss.SSSpell
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException
import java.util.HashSet

class SSAdventure : SpellAdventure<SSSpell>(
    arrayOf(
        AdventureFragmentRunner(R.string.vitalStats, AdventureVitalStatsFragment::class),
        AdventureFragmentRunner(R.string.fights, AdventureCombatFragment::class),
        AdventureFragmentRunner(R.string.spells, AdventureSpellsFragment::class),
        AdventureFragmentRunner(R.string.goldEquipment, AdventureEquipmentFragment::class),
        AdventureFragmentRunner(R.string.map, SSAdventureMapFragment::class),
        AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
    )
) {
    var visitedClearings: Set<String> = HashSet()

    override val spellList: List<SSSpell> = SSSpell.values().toList()

    override val isSpellSingleUse: Boolean
        get() = true

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
        bw.write("clearings=" + Adventure.Companion.arrayToString(visitedClearings) + "\n")
        bw.write("gold=" + gold + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        gold = Integer.valueOf(savedGame.getProperty("gold"))

        chosenSpells = stringToArraySpells(
            String(
                savedGame.getProperty("spells").toByteArray(
                    java.nio.charset.Charset.forName(
                        "ISO-8859-1"
                    )
                )
            ), SSSpell::class.java
        )

        visitedClearings = stringToSet(
            String(
                savedGame.getProperty("clearings").toByteArray(
                    java.nio.charset.Charset.forName(
                        "ISO-8859-1"
                    )
                )
            )
        )
    }

    fun addVisitedClearings(clearing: String) {
        visitedClearings = visitedClearings.plus(clearing)
    }

    companion object {

        protected val FRAGMENT_SPELLS = 2
        protected val FRAGMENT_EQUIPMENT = 3
        protected val FRAGMENT_MAP = 4
        protected val FRAGMENT_NOTES = 5
    }
}
