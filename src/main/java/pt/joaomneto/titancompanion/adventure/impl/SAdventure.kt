package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.bnc.BNCAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.s.SAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class SAdventure : Adventure(
    arrayOf(

        AdventureFragmentRunner(
            R.string.vitalStats,
            SAdventureVitalStatsFragment::class
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

    var currentFaith: Int = -1
    var currentInfection: Int = -1

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

        bw.write("currentFaith=" + currentFaith + "\n")
        bw.write("currentInfection=" + currentInfection + "\n")
        bw.write("gold=" + gold + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        currentFaith = Integer.valueOf(savedGame.getProperty("currentFaith"))
        currentInfection = Integer.valueOf(savedGame.getProperty("currentInfection"))
        gold = Integer.valueOf(savedGame.getProperty("gold"))
    }
}
