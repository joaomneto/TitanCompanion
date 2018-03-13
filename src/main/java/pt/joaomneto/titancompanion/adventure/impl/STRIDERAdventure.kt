package pt.joaomneto.titancompanion.adventure.impl

import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.strider.STRIDERAdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.strider.STRIDERAdventureTimeOxygenFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.strider.STRIDERAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class STRIDERAdventure : Adventure(
    arrayOf(
        AdventureFragmentRunner(
            R.string.vitalStats,
            STRIDERAdventureVitalStatsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.timeAndOxygen,
            STRIDERAdventureTimeOxygenFragment::class
        ),
        AdventureFragmentRunner(
            R.string.fights,
            AdventureCombatFragment::class
        ),
        AdventureFragmentRunner(
            R.string.equipment2,
            STRIDERAdventureEquipmentFragment::class
        ),
        AdventureFragmentRunner(
            R.string.notes,
            AdventureNotesFragment::class
        )
    )
) {

    var currentFear: Int = -1
    var time: Int = -1
    var oxygen: Int = -1

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.adventure, menu)
        return true
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("fear=" + currentFear + "\n")
        bw.write("time=" + time + "\n")
        bw.write("oxygen=" + oxygen + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        currentFear = Integer.valueOf(savedGame.getProperty("fear"))
        time = Integer.valueOf(savedGame.getProperty("time"))
        oxygen = Integer.valueOf(savedGame.getProperty("oxygen"))
    }

    fun testFear() {

        val result = DiceRoller.roll2D6().sum <= currentFear

        val message = if (result) R.string.success else R.string.failed
        Adventure.Companion.showAlert(message, this)

        (getFragment(STRIDERAdventureVitalStatsFragment::class))?.refreshScreensFromResume()
    }

    fun increaseTime() {
        this.time = Math.min(time + 1, 48)
    }

    fun decreaseTime() {
        this.time = Math.max(time - 1, 0)
    }

    fun increaseOxygen() {
        this.oxygen = Math.min(oxygen + 1, 20)
    }

    fun decreaseOxygen() {
        this.oxygen = Math.max(oxygen - 1, 0)
    }

    companion object {

        private val FRAGMENT_VITAL_TIME_OXYGEN = 1
        private val FRAGMENT_COMBAT = 2
        private val FRAGMENT_EQUIPMENT = 3
        private val FRAGMENT_NOTES = 4
    }
}
