package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.SpellAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.mr.MRAdventureSpellsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.mr.MRSkill
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException
import java.util.ArrayList
import java.util.Arrays

class MRAdventure : SpellAdventure<MRSkill>(
    arrayOf(
        AdventureFragmentRunner(
            R.string.vitalStats,
            AdventureVitalStatsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.chosenSkills,
            MRAdventureSpellsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.fights,
            AdventureCombatFragment::class
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

    internal var skills: MutableList<MRSkill> = ArrayList()

    val FRAGMENT_VITAL_STATS = 0
    val FRAGMENT_SPELLS = 1
    val FRAGMENT_COMBAT = 2
    val FRAGMENT_EQUIPMENT = 3
    val FRAGMENT_NOTES = 4

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

        bw.write("skills=" + arrayToStringSpells(skills) + "\n")
        bw.write("gold=" + gold + "\n")
    }

    var spells: MutableList<MRSkill>
        get() = skills
        set(skills) {
            this.skills = skills
        }

    override fun loadAdventureSpecificValuesFromFile() {
        gold = Integer.valueOf(savedGame.getProperty("gold"))
        chosenSpells = stringToArraySpells(savedGame.getProperty("skills"), MRSkill::class.java)
    }

    override val spellList: MutableList<MRSkill>
        get() = Arrays.asList(*MRSkill.values())

    override val isSpellSingleUse: Boolean
        get() = false

    companion object
}
