package pt.joaomneto.titancompanion.adventure.impl

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import java.io.BufferedWriter
import java.io.IOException

class SLAdventure : Adventure() {

    var currentLasers = 0
    var currentShields = 0
    var rating = 0
    var starspray = false

    companion object {
        private val FRAGMENT_VITAL_STATS = 0
        private val FRAGMENT_SHIP_STATS = 1
        private var FRAGMENT_COMBAT = 2
        private var FRAGMENT_VEHICLE_COMBAT = 3
        private var FRAGMENT_EQUIPMENT = 4
        private var FRAGMENT_NOTES = 5
    }

    init {

        Adventure.fragmentConfiguration.clear()
        Adventure.fragmentConfiguration.put(FRAGMENT_VITAL_STATS, AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"))
        Adventure.fragmentConfiguration.put(FRAGMENT_SHIP_STATS, AdventureFragmentRunner(R.string.shipAndWeapons, "pt.joaomneto.titancompanion.adventure.impl.fragments.sl.SLStarshipStatsFragment"))
        Adventure.fragmentConfiguration.put(FRAGMENT_COMBAT, AdventureFragmentRunner(R.string.fights, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"))
        Adventure.fragmentConfiguration.put(FRAGMENT_VEHICLE_COMBAT, AdventureFragmentRunner(R.string.combatWeaponClashes, "pt.joaomneto.titancompanion.adventure.impl.fragments.sl.SLWeaponCombatFragment"))
        Adventure.fragmentConfiguration.put(FRAGMENT_EQUIPMENT, AdventureFragmentRunner(R.string.goldEquipment, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"))
        Adventure.fragmentConfiguration.put(FRAGMENT_NOTES, AdventureFragmentRunner(R.string.notes, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment"))
    }

    override fun loadAdventureSpecificValuesFromFile() {
        gold = savedGame.getProperty("gold").toInt()
        rating = savedGame.getProperty("rating").toInt()
        currentLasers = savedGame.getProperty("currentLasers").toInt()
        currentShields = savedGame.getProperty("currentShields").toInt()
        starspray = savedGame.getProperty("starspray").toBoolean()
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("gold=$gold\n")
        bw.write("rating=$rating\n")
        bw.write("currentLasers=$currentLasers\n")
        bw.write("currentShields=$currentShields\n")
        bw.write("starspray=$starspray\n")
    }

    override fun getConsumeProvisionText(): String {
        return resources.getString(R.string.useProvisionTablet)
    }

    override fun getProvisionsText(): String {
        return resources.getString(R.string.provisionTablets)
    }

    override fun getCurrencyName(): String {
        return resources.getString(R.string.credits)
    }
}
