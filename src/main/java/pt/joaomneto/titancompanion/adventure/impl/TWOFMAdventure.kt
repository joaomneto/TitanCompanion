package pt.joaomneto.titancompanion.adventure.impl

import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

open class TWOFMAdventure(
    override val fragmentConfiguration: Array<AdventureFragmentRunner> = DEFAULT_FRAGMENTS
) : Adventure(fragmentConfiguration) {

    companion object {
        val DEFAULT_FRAGMENTS = arrayOf(
            AdventureFragmentRunner(
                R.string.vitalStats,
                AdventureVitalStatsFragment::class
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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.adventure, menu)
        return true
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("standardPotion=" + standardPotion + "\n")
        bw.write(
            "standardPotionValue=" + standardPotionValue
                + "\n"
        )
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
    }
}
