package pt.joaomneto.titancompanion.adventure.impl

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sota.SOTAAdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sota.SOTAAdventureTimeFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

class SOTAAdventure : TFODAdventure(
    arrayOf(
        AdventureFragmentRunner(R.string.vitalStats, AdventureVitalStatsFragment::class),
        AdventureFragmentRunner(R.string.time, SOTAAdventureTimeFragment::class),
        AdventureFragmentRunner(R.string.fights, SOTAAdventureCombatFragment::class),
        AdventureFragmentRunner(R.string.goldEquipment, AdventureEquipmentFragment::class),
        AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
    )
) {

    var time = 0

    override fun loadAdventureSpecificValuesFromFile() {
        gold = Integer.valueOf(savedGame.getProperty("gold"))
        provisions = Integer.valueOf(savedGame.getProperty("provisions"))
        provisionsValue = Integer.valueOf(savedGame.getProperty("provisionsValue"))
        time = Integer.valueOf(savedGame.getProperty("time"))
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("gold=$gold\n")
        bw.write("time=$time\n")
        bw.write("provisions=$provisions\n")
        bw.write("provisionsValue=4\n")
    }

}
