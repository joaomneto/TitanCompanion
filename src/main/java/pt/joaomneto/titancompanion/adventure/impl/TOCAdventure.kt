package pt.joaomneto.titancompanion.adventure.impl


import pt.joaomneto.titancompanion.R
import android.support.v4.app.Fragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import java.io.BufferedWriter
import java.io.IOException

class TOCAdventure : TFODAdventure(
        arrayOf(
                AdventureFragmentRunner(R.string.vitalStats, AdventureVitalStatsFragment::class),
                AdventureFragmentRunner(R.string.fights, AdventureCombatFragment::class),
                AdventureFragmentRunner(R.string.goldEquipment, AdventureEquipmentFragment::class),
                AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class))) {

    override fun loadAdventureSpecificValuesFromFile() {
        gold = Integer.valueOf(savedGame.getProperty("gold"))
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("gold=" + gold + "\n")
    }

    companion object {

        private val FRAGMENT_EQUIPMENT = 2
        private val FRAGMENT_NOTES = 3
    }

}
