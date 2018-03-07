package pt.joaomneto.titancompanion.adventure.impl

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sl.SLAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sl.SLStarshipStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sl.SLWeaponCombatFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

class SLAdventure : Adventure(
    arrayOf(
        AdventureFragmentRunner(R.string.vitalStats, SLAdventureVitalStatsFragment::class),
        AdventureFragmentRunner(R.string.shipAndWeapons, SLStarshipStatsFragment::class),
        AdventureFragmentRunner(R.string.fights, AdventureCombatFragment::class),
        AdventureFragmentRunner(R.string.combatWeaponClashes, SLWeaponCombatFragment::class),
        AdventureFragmentRunner(R.string.goldEquipment, AdventureEquipmentFragment::class),
        AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
    )
) {

    var currentLasers = 0
    var currentShields = 0
    var rating = 0
    var starspray = false
    var oxygen = 0

    companion object {
        private val FRAGMENT_VITAL_STATS = 0
        private val FRAGMENT_SHIP_STATS = 1
        private var FRAGMENT_COMBAT = 2
        private var FRAGMENT_VEHICLE_COMBAT = 3
        private var FRAGMENT_EQUIPMENT = 4
        private var FRAGMENT_NOTES = 5
    }

    override fun loadAdventureSpecificValuesFromFile() {
        gold = savedGame.getProperty("gold").toInt()
        rating = savedGame.getProperty("rating").toInt()
        currentLasers = savedGame.getProperty("currentLasers").toInt()
        currentShields = savedGame.getProperty("currentShields").toInt()
        starspray = savedGame.getProperty("starspray").toBoolean()
        oxygen = savedGame.getProperty("oxygen").toInt()
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("gold=$gold\n")
        bw.write("rating=$rating\n")
        bw.write("currentLasers=$currentLasers\n")
        bw.write("currentShields=$currentShields\n")
        bw.write("starspray=$starspray\n")
        bw.write("oxygen=$oxygen\n")
    }

    override val consumeProvisionText = R.string.useProvisionTablet

    override val provisionsText = R.string.provisionTablets

    override val currencyName = R.string.credits
}
