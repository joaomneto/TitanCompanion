package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.ff.FFAdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.ff.FFVehicleCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.ff.FFVehicleStatsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException
import java.util.ArrayList

class FFAdventure : Adventure(
    arrayOf(
        AdventureFragmentRunner(R.string.vitalStats, AdventureVitalStatsFragment::class),
        AdventureFragmentRunner(R.string.fights, FFAdventureCombatFragment::class),
        AdventureFragmentRunner(R.string.vehicleCombat, FFVehicleCombatFragment::class),
        AdventureFragmentRunner(R.string.vehicleSpecs, FFVehicleStatsFragment::class),
        AdventureFragmentRunner(R.string.goldEquipment, AdventureEquipmentFragment::class),
        AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
    )
) {

    var carEnhancements: List<String> = ArrayList()
    var currentFirepower = -1
    var currentArmour = -1
    var initialFirepower = -1
    var initialArmour = -1
    var rockets = -1
    var ironSpikes = -1
    var oilCannisters = -1
    var spareWheels = -1

    override val consumeProvisionText = R.string.useMedKit

    override val provisionsText = R.string.medKits

    override val currencyName = R.string.credits

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

        bw.write("currentFirepower=" + currentFirepower + "\n")
        bw.write("currentArmour=" + currentArmour + "\n")
        bw.write("initialFirepower=" + initialFirepower + "\n")
        bw.write("initialArmour=" + initialArmour + "\n")
        bw.write("rockets=" + rockets + "\n")
        bw.write("ironSpikes=" + ironSpikes + "\n")
        bw.write("oilCannisters=" + oilCannisters + "\n")
        bw.write("spareWheels=" + spareWheels + "\n")

        bw.write("gold=" + gold + "\n")
        bw.write("provisions=" + provisions + "\n")
        bw.write("provisionsValue=" + provisionsValue + "\n")
        bw.write("carEnhancements=" + Adventure.Companion.arrayToString(carEnhancements))
    }

    override fun loadAdventureSpecificValuesFromFile() {

        currentFirepower = Integer.valueOf(savedGame.getProperty("currentFirepower"))
        currentArmour = Integer.valueOf(savedGame.getProperty("currentArmour"))
        initialFirepower = Integer.valueOf(savedGame.getProperty("initialFirepower"))
        initialArmour = Integer.valueOf(savedGame.getProperty("initialArmour"))
        rockets = Integer.valueOf(savedGame.getProperty("rockets"))
        ironSpikes = Integer.valueOf(savedGame.getProperty("ironSpikes"))
        oilCannisters = Integer.valueOf(savedGame.getProperty("oilCannisters"))
        gold = Integer.valueOf(savedGame.getProperty("gold"))
        provisions = Integer.valueOf(savedGame.getProperty("provisions"))
        provisionsValue = Integer.valueOf(savedGame.getProperty("provisionsValue"))
        spareWheels = Integer.valueOf(savedGame.getProperty("spareWheels"))
        carEnhancements = stringToArray(savedGame.getProperty("carEnhancements"))
    }

    companion object {

        protected val FRAGMENT_VEHICLE_COMBAT = 2
        protected val FRAGMENT_VEHICLE_EQUIPMENT = 3
        protected val FRAGMENT_EQUIPMENT = 4
        protected val FRAGMENT_NOTES = 5
    }
}
