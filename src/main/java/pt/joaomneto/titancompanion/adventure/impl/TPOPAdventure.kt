package pt.joaomneto.titancompanion.adventure.impl


import java.io.BufferedWriter
import java.io.IOException

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure

class TPOPAdventure : TWOFMAdventure() {

    var copper = 0

    init {
        Adventure.fragmentConfiguration.put(Adventure.FRAGMENT_VITAL_STATS, Adventure.AdventureFragmentRunner(R.string.vitalStats,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"))
        Adventure.fragmentConfiguration.put(Adventure.FRAGMENT_COMBAT, Adventure.AdventureFragmentRunner(R.string.fights,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"))
        Adventure.fragmentConfiguration.put(Adventure.FRAGMENT_EQUIPMENT, Adventure.AdventureFragmentRunner(R.string.goldEquipment,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tpop.TPOPAdventureEquipmentFragment"))
        Adventure.fragmentConfiguration.put(Adventure.FRAGMENT_NOTES, Adventure.AdventureFragmentRunner(R.string.notes,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment"))

    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("copper=" + copper + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        val copper = savedGame.getProperty("copper")

        this.copper = Integer.valueOf(if (copper != null && copper.length > 0) copper else "0")!!
    }

}
