package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.bnc.BNCAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class BNCAdventure : Adventure(
    arrayOf(

        AdventureFragmentRunner(
            R.string.vitalStats,
            BNCAdventureVitalStatsFragment::class
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

    var initialWillpower: Int = -1
    var currentWillpower: Int = -1

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

        bw.write("currentWillpower=" + currentWillpower + "\n")
        bw.write("initialWillpower=" + initialWillpower + "\n")
        bw.write("gold=" + gold + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        currentWillpower = Integer.valueOf(savedGame.getProperty("currentWillpower"))
        initialWillpower = Integer.valueOf(savedGame.getProperty("initialWillpower"))
        gold = Integer.valueOf(savedGame.getProperty("gold"))
    }

    fun testWillpower() {

        val result = DiceRoller.roll2D6().sum <= currentWillpower

        currentWillpower = Math.max(0, currentWillpower - 1)

        val message = if (result) R.string.success else R.string.failed
        Adventure.Companion.showAlert(message, this)

        getFragment(BNCAdventureVitalStatsFragment::class)?.refreshScreensFromResume()
    }
}
