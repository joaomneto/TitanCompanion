package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sob.SOBAdventureBootyFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sob.SOBAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sob.SOBShipCombatFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException
import java.util.ArrayList

class SOBAdventure : Adventure(
    arrayOf(
        AdventureFragmentRunner(R.string.vitalStats, SOBAdventureVitalStatsFragment::class),
        AdventureFragmentRunner(R.string.fights, AdventureCombatFragment::class),
        AdventureFragmentRunner(R.string.shipCombat, SOBShipCombatFragment::class),
        AdventureFragmentRunner(R.string.goldEquipment, AdventureEquipmentFragment::class),
        AdventureFragmentRunner(R.string.booty, SOBAdventureBootyFragment::class),
        AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
    )
) {

    var currentCrewStrength = -1
    var currentCrewStrike = -1
    var initialCrewStrength = -1
    var initialCrewStrike = -1
    var log = -1
    var booty: List<String> = ArrayList()

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

        bw.write("currentCrewStrength=" + currentCrewStrength + "\n")
        bw.write("currentCrewStrike=" + currentCrewStrike + "\n")
        bw.write("initialCrewStrength=" + initialCrewStrength + "\n")
        bw.write("initialCrewStrike=" + initialCrewStrike + "\n")
        bw.write("log=" + log + "\n")
        bw.write("gold=" + gold + "\n")
        bw.write("booty=" + Adventure.Companion.arrayToString(booty) + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {

        currentCrewStrength = Integer.valueOf(savedGame.getProperty("currentCrewStrength"))
        currentCrewStrike = Integer.valueOf(savedGame.getProperty("currentCrewStrike"))
        initialCrewStrength = Integer.valueOf(savedGame.getProperty("initialCrewStrength"))
        initialCrewStrike = Integer.valueOf(savedGame.getProperty("initialCrewStrike"))
        log = Integer.valueOf(savedGame.getProperty("log"))
        gold = Integer.valueOf(savedGame.getProperty("gold"))
        booty = stringToArray(savedGame.getProperty("booty"))
    }

    companion object {

        protected val FRAGMENT_SHIP_COMBAT = 2
        protected val FRAGMENT_EQUIPMENT = 3
        protected val FRAGMENT_BOOTY = 4
        protected val FRAGMENT_NOTES = 5
    }
}
