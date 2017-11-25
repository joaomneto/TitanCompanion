package pt.joaomneto.titancompanion.adventure.impl


import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import java.io.BufferedWriter
import java.io.IOException

class SLAdventure : Adventure() {

    var currentLasers= 0
    var currentShields= 0
    var initialLasers= 0
    var initialShields= 0
    var rating = 0

    companion object {
        private val FRAGMENT_VITAL_STATS = 0
        private val FRAGMENT_SHIP_STATS = 1
        private val FRAGMENT_COMBAT = 2
        private val FRAGMENT_VEHICLE_COMBAT = 3
        private val FRAGMENT_EQUIPMENT = 4
        private val FRAGMENT_NOTES = 5
    }

    init {
        Adventure.fragmentConfiguration.clear()
        Adventure.fragmentConfiguration.put(FRAGMENT_VITAL_STATS, Adventure.AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"))
        Adventure.fragmentConfiguration.put(FRAGMENT_SHIP_STATS, Adventure.AdventureFragmentRunner(R.string.starship2, "pt.joaomneto.titancompanion.adventure.impl.fragments.sl.SLStarshipStatsFragment"))
        Adventure.fragmentConfiguration.put(FRAGMENT_COMBAT, Adventure.AdventureFragmentRunner(R.string.fights, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"))
        Adventure.fragmentConfiguration.put(FRAGMENT_VEHICLE_COMBAT, Adventure.AdventureFragmentRunner(R.string.combatWeaponClashes, "pt.joaomneto.titancompanion.adventure.impl.fragments.sl.SLWeaponCombatFragment"))
        Adventure.fragmentConfiguration.put(FRAGMENT_EQUIPMENT, Adventure.AdventureFragmentRunner(R.string.goldEquipment, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"))
        Adventure.fragmentConfiguration.put(FRAGMENT_NOTES, Adventure.AdventureFragmentRunner(R.string.notes, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment"))
    }

    override fun loadAdventureSpecificValuesFromFile() {
        gold = Integer.valueOf(savedGame.getProperty("gold"))
        rating = Integer.valueOf(savedGame.getProperty("rating"))
        initialLasers = Integer.valueOf(savedGame.getProperty("initialLasers"))
        currentLasers = Integer.valueOf(savedGame.getProperty("currentLasers"))
        initialShields = Integer.valueOf(savedGame.getProperty("initialShields"))
        currentShields = Integer.valueOf(savedGame.getProperty("currentShields"))
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("gold=$gold\n")
        bw.write("rating=$rating\n")
        bw.write("initialLasers=$initialLasers\n")
        bw.write("currentLasers=$currentLasers\n")
        bw.write("initialShields=$initialShields\n")
        bw.write("currentShields=$currentShields\n")
    }

    override fun getConsumeProvisionText(): String {
        return resources.getString(R.string.useProvisionTablet)
    }

    fun getProvisionsText(): String {
        return resources.getString(R.string.provisionTablets)
    }

    override fun getCurrencyName(): String {
        return resources.getString(R.string.credits)
    }
}
