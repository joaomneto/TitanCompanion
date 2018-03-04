package pt.joaomneto.titancompanion.adventure.impl


import pt.joaomneto.titancompanion.R
import android.support.v4.app.Fragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tpop.TPOPAdventureEquipmentFragment
import java.io.BufferedWriter
import java.io.IOException

class TPOPAdventure : TWOFMAdventure(
        arrayOf(
                AdventureFragmentRunner(R.string.vitalStats,
                        AdventureVitalStatsFragment::class),
                AdventureFragmentRunner(R.string.fights,
                        AdventureCombatFragment::class),
                AdventureFragmentRunner(R.string.goldEquipment,
                        TPOPAdventureEquipmentFragment::class),
                AdventureFragmentRunner(R.string.notes,
                        AdventureNotesFragment::class))) {

    var copper = 0

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("copper=" + copper + "\n")
        bw.write("gold=" + gold + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        val copperS = savedGame.getProperty("copper")
        val goldS = savedGame.getProperty("gold")

        this.copper = if (copperS.isNullOrBlank()) 0 else Integer.parseInt(copperS)
        this.gold = if (copperS.isNullOrBlank()) 0 else Integer.parseInt(goldS)
    }

}
