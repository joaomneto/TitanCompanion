package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.pof.POFAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

class POFAdventure : Adventure(
    arrayOf(
        AdventureFragmentRunner(R.string.vitalStats, POFAdventureVitalStatsFragment::class),
        AdventureFragmentRunner(R.string.fights, AdventureCombatFragment::class),
        AdventureFragmentRunner(R.string.goldEquipment, AdventureEquipmentFragment::class),
        AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
    )
) {
    var currentPower = -1
    var initialPower = -1

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

        bw.write("currentPower=" + currentPower + "\n")
        bw.write("initialPower=" + initialPower + "\n")
        bw.write("standardPotion=" + standardPotion + "\n")
        bw.write("standardPotionValue=" + standardPotionValue + "\n")
        bw.write("provisions=0\n")
        bw.write("provisionsValue=4\n")
        bw.write("gold=" + gold + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        standardPotion = Integer.valueOf(
            savedGame
                .getProperty("standardPotion")
        )
        standardPotionValue = Integer.valueOf(
            savedGame
                .getProperty("standardPotionValue")
        )
        gold = Integer.valueOf(savedGame.getProperty("gold"))
        currentPower = Integer.valueOf(savedGame.getProperty("currentPower"))
        initialPower = Integer.valueOf(savedGame.getProperty("initialPower"))
    }

    companion object {

        protected val FRAGMENT_EQUIPMENT = 2
        protected val FRAGMENT_NOTES = 3
    }
}
