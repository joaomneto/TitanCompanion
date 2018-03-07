package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.awf.AWFAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

class AWFAdventure : Adventure(
    arrayOf(

        AdventureFragmentRunner(R.string.vitalStats, AWFAdventureVitalStatsFragment::class),
        AdventureFragmentRunner(R.string.fights, AdventureCombatFragment::class),
        AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
    )
) {

    var heroPoints: Int = 0
    var superPower: String? = null

    override val combatSkillValue: Int
        get() = if (superPower == getString(R.string.awfSuperStrength)) {
            13
        } else getCurrentSkill()

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
        bw.write("heroPoints=" + heroPoints + "\n")
        bw.write("superPower=" + superPower + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        heroPoints = Integer.valueOf(savedGame.getProperty("heroPoints"))
        superPower = savedGame.getProperty("superPower")
    }

    companion object {

        internal val FRAGMENT_NOTES = 2
    }
}
