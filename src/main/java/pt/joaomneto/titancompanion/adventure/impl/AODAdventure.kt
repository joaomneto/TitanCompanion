package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.aod.AODAdventureSoldiersFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.aod.Army
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

class AODAdventure : Adventure(
    arrayOf(
        AdventureFragmentRunner(R.string.vitalStats, AdventureVitalStatsFragment::class),
        AdventureFragmentRunner(R.string.soldiers, AODAdventureSoldiersFragment::class),
        AdventureFragmentRunner(R.string.fights, AdventureCombatFragment::class),
        AdventureFragmentRunner(R.string.goldTreasure, AdventureEquipmentFragment::class),
        AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
    )
) {

    var soldiers = Army()

    override val currencyName = R.string.gold

    val soldiersFragment: AODAdventureSoldiersFragment?
        get() = getFragment(AODAdventureSoldiersFragment::class)

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

        bw.write("gold=" + gold + "\n")
        bw.write("soldiers=" + soldiers.stringToSaveGame)
    }

    override fun loadAdventureSpecificValuesFromFile() {
        gold = Integer.valueOf(savedGame.getProperty("gold"))
        soldiers = Army.getInstanceFromSavedString(savedGame.getProperty("soldiers"))
    }

    companion object {

        val FRAGMENT_SOLDIERS = 1
        val FRAGMENT_COMBAT = 2
        val FRAGMENT_EQUIPMENT = 3
        val FRAGMENT_NOTES = 4
    }
}
