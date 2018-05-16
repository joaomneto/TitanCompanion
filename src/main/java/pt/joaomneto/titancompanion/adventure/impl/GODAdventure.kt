package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.god.GODAdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.god.GODAdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.god.GODAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.god.GODWeapon
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

class GODAdventure : Adventure(
    arrayOf(
        AdventureFragmentRunner(R.string.vitalStats, GODAdventureVitalStatsFragment::class),
        AdventureFragmentRunner(R.string.fights, GODAdventureCombatFragment::class),
        AdventureFragmentRunner(R.string.goldEquipment, GODAdventureEquipmentFragment::class),
        AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
    )
) {

    var weapon: GODWeapon = GODWeapon.UNARMED
    var poison: Int = 0
    var smokeOil: Int = 0

    override val currencyName = R.string.gold

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

        bw.write("gold=$gold\n")
        bw.write("weapon=${weapon.name}\n")
        bw.write("poison=$poison\n")
        bw.write("smokeOil=$smokeOil\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        gold = Integer.valueOf(savedGame.getProperty("gold"))
        poison = Integer.valueOf(savedGame.getProperty("poison"))
        weapon = GODWeapon.valueOf(savedGame.getProperty("weapon"))
        smokeOil = Integer.valueOf(savedGame.getProperty("smokeOil"))
    }
}
