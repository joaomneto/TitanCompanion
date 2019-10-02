package pt.joaomneto.titancompanion.adventure.impl

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter

open class EOTDAdventure : TFODAdventure(
    arrayOf(
        AdventureFragmentRunner(R.string.vitalStats, AdventureVitalStatsFragment::class),
        AdventureFragmentRunner(R.string.fights, AdventureCombatFragment::class),
        AdventureFragmentRunner(R.string.goldEquipment, AdventureEquipmentFragment::class),
        AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
    )
) {

    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("gold=$gold\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        gold = Integer.valueOf(savedGame.getProperty("gold"))
    }
}
