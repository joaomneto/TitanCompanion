package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.SpellAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.mr.MRSkill
import java.io.BufferedWriter
import java.io.IOException
import java.util.*

class MRAdventure : SpellAdventure<MRSkill>() {

    internal var skills: MutableList<MRSkill> = ArrayList()

    val FRAGMENT_SPELLS = 2
    val FRAGMENT_EQUIPMENT = 3
    val FRAGMENT_NOTES = 4

    init {
        Adventure.fragmentConfiguration.clear()
        Adventure.fragmentConfiguration.put(Adventure.FRAGMENT_VITAL_STATS, Adventure.AdventureFragmentRunner(R.string.vitalStats,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"))
        Adventure.fragmentConfiguration.put(Adventure.FRAGMENT_COMBAT, Adventure.AdventureFragmentRunner(R.string.fights,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"))
        Adventure.fragmentConfiguration.put(FRAGMENT_SPELLS, Adventure.AdventureFragmentRunner(R.string.chosenSkills,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureSpellsFragment"))
        Adventure.fragmentConfiguration.put(FRAGMENT_EQUIPMENT, Adventure.AdventureFragmentRunner(R.string.goldEquipment,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"))
        Adventure.fragmentConfiguration.put(FRAGMENT_NOTES, Adventure.AdventureFragmentRunner(R.string.notes,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment"))
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
        chosenSpells = stringToArraySpells(String(savedGame.getProperty("skills").toByteArray(java.nio.charset.Charset.forName("ISO-8859-1"))), MRSkill::class.java)

    }

    override val spellList: MutableList<MRSkill>
        get() = Arrays.asList(*MRSkill.values())

    override val isSpellSingleUse: Boolean
        get() = false

    companion object

}
