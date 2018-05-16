package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sa.SAAdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sa.SAAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sa.SAAdventureWeaponsFragment
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa.SAWeapon
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException
import java.util.ArrayList

class SAAdventure : Adventure(
    arrayOf(

        AdventureFragmentRunner(
            R.string.vitalStats,
            SAAdventureVitalStatsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.fights,
            SAAdventureCombatFragment::class
        ),
        AdventureFragmentRunner(
            R.string.title_adventure_creation_weapons,
            SAAdventureWeaponsFragment::class
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
) {

    var currentArmor: Int? = null
    internal var weapons: MutableList<SAWeapon> = ArrayList()

    override val consumeProvisionText = R.string.usePepPill

    override val provisionsText = R.string.pepPills

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

        bw.write("currentArmor=" + currentArmor + "\n")
        bw.write("weaponsStat=" + Adventure.Companion.arrayToString(weapons) + "\n")
        bw.write("provisions=4\n")
        bw.write("provisionsValue=5\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        currentArmor = Integer.valueOf(savedGame.getProperty("currentArmor"))
        val weaponsStrings = stringToArray(savedGame.getProperty("weaponsStat"))
        for (weaponName in weaponsStrings) {
            weapons.add(SAWeapon.valueOf(weaponName))
        }
    }

    fun getWeapons(): List<SAWeapon> {
        return weapons
    }

    fun setWeapons(weapons: MutableList<SAWeapon>) {
        this.weapons = weapons
    }

    companion object {

        internal val FRAGMENT_WEAPONS = 2
        internal val FRAGMENT_EQUIPMENT = 3
        internal val FRAGMENT_NOTES = 4
    }
}
