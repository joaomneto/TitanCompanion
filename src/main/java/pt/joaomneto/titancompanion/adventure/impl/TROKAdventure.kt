package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.trok.TROKAdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.trok.TROKStarShipCombatFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

class TROKAdventure : Adventure(
    arrayOf(
        AdventureFragmentRunner(R.string.vitalStats, AdventureVitalStatsFragment::class),
        AdventureFragmentRunner(R.string.fights, TROKAdventureCombatFragment::class),
        AdventureFragmentRunner(R.string.starshipCombat, TROKStarShipCombatFragment::class),
        AdventureFragmentRunner(R.string.moneyEquipment, AdventureEquipmentFragment::class),
        AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
    )
) {
    var currentWeapons = -1
    var currentShields = -1
    var initialWeapons = -1
    var initialShields = -1
    var missiles = -1

    override val consumeProvisionText = R.string.usePepPill

    override val provisionsText = R.string.pepPills

    override val currencyName = R.string.kopecks

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

        bw.write("currentWeapons=" + currentWeapons + "\n")
        bw.write("currentShields=" + currentShields + "\n")
        bw.write("initialWeapons=" + initialWeapons + "\n")
        bw.write("initialShields=" + initialShields + "\n")
        bw.write("missiles=" + missiles + "\n")
        bw.write("gold=" + gold + "\n")
        bw.write("provisions=" + provisions + "\n")
        bw.write("provisionsValue=" + provisionsValue + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {

        currentWeapons = Integer.valueOf(savedGame.getProperty("currentWeapons"))
        currentShields = Integer.valueOf(savedGame.getProperty("currentShields"))
        initialWeapons = Integer.valueOf(savedGame.getProperty("initialWeapons"))
        initialShields = Integer.valueOf(savedGame.getProperty("initialShields"))
        missiles = Integer.valueOf(savedGame.getProperty("missiles"))
        provisions = Integer.valueOf(savedGame.getProperty("provisions"))
        provisionsValue = Integer.valueOf(savedGame.getProperty("provisionsValue"))
        gold = Integer.valueOf(savedGame.getProperty("gold"))
    }

    companion object {

        protected val FRAGMENT_VEHICLE_COMBAT = 2
        protected val FRAGMENT_EQUIPMENT = 3
        protected val FRAGMENT_NOTES = 4
    }
}
