//package pt.joaomneto.titancompanion.adventurecreation.impl
//
//import android.view.View
//import pt.joaomneto.titancompanion.R
//import pt.joaomneto.titancompanion.adventure.Adventure
//import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
//import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa.SAVitalStatisticsFragment
//import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa.SAWeapon
//import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa.SAWeaponsFragment
//import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
//import pt.joaomneto.titancompanion.util.DiceRoller
//import java.io.BufferedWriter
//import java.io.IOException
//
//class SAAdventureCreation : AdventureCreation(
//    arrayOf(
//        AdventureFragmentRunner(
//            R.string.title_adventure_creation_vitalstats,
//            SAVitalStatisticsFragment::class
//        ),
//        AdventureFragmentRunner(
//            R.string.title_adventure_creation_weapons,
//            SAWeaponsFragment::class
//        )
//    )
//) {
//
//    var currentArmor = -1
//    var currentWeapons = -1
//    var weapons: MutableList<SAWeapon> = mutableListOf()
//
//    private val saVitalStatisticsFragmentt: SAVitalStatisticsFragment?
//        get() = getFragment(SAVitalStatisticsFragment::class)
//
//    private val saWeaponsFragment: SAWeaponsFragment?
//        get() = getFragment(SAWeaponsFragment::class)
//
//    init {
//
//    }
//
//    @Throws(IOException::class)
//    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
//
//        bw.write("currentArmor=" + currentArmor + "\n")
//        bw.write("currentWeapons=" + currentWeapons + "\n")
//        bw.write("weaponsStat=" + Adventure.arrayToString(weapons) + "\n")
//        bw.write("provisions=4\n")
//        bw.write("provisionsValue=5\n")
//    }
//
//    override fun rollGamebookSpecificStats(view: View) {
//        currentArmor = DiceRoller.rollD6() + 6
//        saVitalStatisticsFragmentt?.armorValue?.text = currentArmor.toString()
//        currentWeapons = DiceRoller.rollD6()
//        saWeaponsFragment?.weaponsValue?.text = currentWeapons.toString()
//    }
//
//    override fun validateCreationSpecificParameters(): String? {
//        val sb = StringBuilder()
//        if (this.currentArmor < 0 || this.weapons.isEmpty()) {
//            sb.append(getString(R.string.weaponsArmorMandatory))
//        }
//        return sb.toString()
//    }
//}
