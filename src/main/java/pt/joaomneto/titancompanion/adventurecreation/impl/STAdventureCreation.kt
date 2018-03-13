package pt.joaomneto.titancompanion.adventurecreation.impl

import android.view.View
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.st.STCrewAndShipVitalStatisticsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException

class STAdventureCreation : AdventureCreation(
    arrayOf(
        AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            VitalStatisticsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.shipCrewStats,
            STCrewAndShipVitalStatisticsFragment::class
        )
    )
) {

    var scienceOfficerSkill = -1
    var medicalOfficerSkill = -1
    var engineeringOfficerSkill = -1
    var securityOfficerSkill = -1
    var securityGuard1Skill = -1
    var securityGuard2Skill = -1
    var shipWeapons = -1

    var scienceOfficerStamina = -1
    var medicalOfficerStamina = -1
    var engineeringOfficerStamina = -1
    var securityOfficerStamina = -1
    var securityGuard1Stamina = -1
    var securityGuard2Stamina = -1
    var shipShields = -1

    private val stCrewAndShipVitalStatisticsFragment: STCrewAndShipVitalStatisticsFragment?
        get() = getFragment(STCrewAndShipVitalStatisticsFragment::class)

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("scienceOfficerSkill=" + scienceOfficerSkill + "\n")
        bw.write("scienceOfficerStamina=" + scienceOfficerStamina + "\n")
        bw.write("medicalOfficerSkill=" + medicalOfficerSkill + "\n")
        bw.write("medicalOfficerStamina=" + medicalOfficerStamina + "\n")
        bw.write("engineeringOfficerSkill=" + engineeringOfficerSkill + "\n")
        bw.write("engineeringOfficerStamina=" + engineeringOfficerStamina + "\n")
        bw.write("securityOfficerSkill=" + securityOfficerSkill + "\n")
        bw.write("securityOfficerStamina=" + securityOfficerStamina + "\n")
        bw.write("securityGuard1Skill=" + securityGuard1Skill + "\n")
        bw.write("securityGuard1Stamina=" + securityGuard1Stamina + "\n")
        bw.write("securityGuard2Skill=" + securityGuard2Skill + "\n")
        bw.write("securityGuard2Stamina=" + securityGuard2Stamina + "\n")
        bw.write("shipWeapons=" + shipWeapons + "\n")
        bw.write("shipShields=" + shipShields + "\n")

        bw.write("scienceOfficerInitialSkill=" + scienceOfficerSkill + "\n")
        bw.write("scienceOfficerInitialStamina=" + scienceOfficerStamina + "\n")
        bw.write("medicalOfficerInitialSkill=" + medicalOfficerSkill + "\n")
        bw.write("medicalOfficerInitialStamina=" + medicalOfficerStamina + "\n")
        bw.write("engineeringOfficerInitialSkill=" + engineeringOfficerSkill + "\n")
        bw.write("engineeringOfficerInitialStamina=" + engineeringOfficerStamina + "\n")
        bw.write("securityOfficerInitialSkill=" + securityOfficerSkill + "\n")
        bw.write("securityOfficerInitialStamina=" + securityOfficerStamina + "\n")
        bw.write("securityGuard1InitialSkill=" + securityGuard1Skill + "\n")
        bw.write("securityGuard1InitialStamina=" + securityGuard1Stamina + "\n")
        bw.write("securityGuard2InitialSkill=" + securityGuard2Skill + "\n")
        bw.write("securityGuard2InitialStamina=" + securityGuard2Stamina + "\n")
        bw.write("shipInitialWeapons=" + shipWeapons + "\n")
        bw.write("shipInitialShields=" + shipShields + "\n")
    }

    override fun rollGamebookSpecificStats(view: View) {
        scienceOfficerSkill = DiceRoller.rollD6() + 6
        scienceOfficerStamina = DiceRoller.roll2D6().sum + 12
        medicalOfficerSkill = DiceRoller.rollD6() + 6
        medicalOfficerStamina = DiceRoller.roll2D6().sum + 12
        engineeringOfficerSkill = DiceRoller.rollD6() + 6
        engineeringOfficerStamina = DiceRoller.roll2D6().sum + 12
        securityOfficerSkill = DiceRoller.rollD6() + 6
        securityOfficerStamina = DiceRoller.roll2D6().sum + 12
        securityGuard1Skill = DiceRoller.rollD6() + 6
        securityGuard1Stamina = DiceRoller.roll2D6().sum + 12
        securityGuard2Skill = DiceRoller.rollD6() + 6
        securityGuard2Stamina = DiceRoller.roll2D6().sum + 12
        shipWeapons = DiceRoller.rollD6() + 6
        shipShields = DiceRoller.roll2D6().sum + 12
        stCrewAndShipVitalStatisticsFragment?.updateFields()
    }

    override fun validateCreationSpecificParameters(): String? {
        val sb = StringBuilder()
        if (this.shipShields < 0) {
            sb.append(getString(R.string.starshipCrewStatsMandatory))
        }
        return sb.toString()
    }
}
