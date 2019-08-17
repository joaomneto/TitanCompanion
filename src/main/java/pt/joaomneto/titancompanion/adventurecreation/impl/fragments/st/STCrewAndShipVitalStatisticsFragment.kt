package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.st

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.STAdventureCreation

class STCrewAndShipVitalStatisticsFragment : Fragment() {

    internal var scienceOfficerSkill: TextView? = null
    internal var medicalOfficerSkill: TextView? = null
    internal var engineeringOfficerSkill: TextView? = null
    internal var securityOfficerSkill: TextView? = null
    internal var securityGuard1Skill: TextView? = null
    internal var securityGuard2Skill: TextView? = null
    internal var shipWeapons: TextView? = null

    internal var scienceOfficerStamina: TextView? = null
    internal var medicalOfficerStamina: TextView? = null
    internal var engineeringOfficerStamina: TextView? = null
    internal var securityOfficerStamina: TextView? = null
    internal var securityGuard1Stamina: TextView? = null
    internal var securityGuard2Stamina: TextView? = null
    internal var shipShields: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(
            R.layout.fragment_04st_adventurecreation_crewshipvitalstats,
            container, false
        )

        scienceOfficerSkill = rootView
            .findViewById(R.id.scienceOfficerSkillValue)
        medicalOfficerSkill = rootView
            .findViewById(R.id.medicalOfficerSkillValue)
        engineeringOfficerSkill = rootView
            .findViewById(R.id.engineeringOfficerSkillValue)
        securityOfficerSkill = rootView
            .findViewById(R.id.securityOfficerSkillValue)
        securityGuard1Skill = rootView
            .findViewById(R.id.securityGuard1SkillValue)
        securityGuard2Skill = rootView
            .findViewById(R.id.securityGuard2SkillValue)
        shipWeapons = rootView.findViewById(R.id.shipWeaponsValue)

        scienceOfficerStamina = rootView
            .findViewById(R.id.scienceOfficerStaminaValue)
        medicalOfficerStamina = rootView
            .findViewById(R.id.medicalOfficerStaminaValue)
        engineeringOfficerStamina = rootView
            .findViewById(R.id.engineeringOfficerStaminaValue)
        securityOfficerStamina = rootView
            .findViewById(R.id.securityOfficerStaminaValue)
        securityGuard1Stamina = rootView
            .findViewById(R.id.securityGuard1StaminaValue)
        securityGuard2Stamina = rootView
            .findViewById(R.id.securityGuard2StaminaValue)
        shipShields = rootView.findViewById(R.id.shipShieldsValue)

        return rootView
    }

    fun updateFields() {
        val act = activity as STAdventureCreation?

        scienceOfficerSkill!!.text = "" + act!!.scienceOfficerSkill
        medicalOfficerSkill!!.text = "" + act.medicalOfficerSkill
        engineeringOfficerSkill!!.text = "" + act.engineeringOfficerSkill
        securityOfficerSkill!!.text = "" + act.securityOfficerSkill
        securityGuard1Skill!!.text = "" + act.securityGuard1Skill
        securityGuard2Skill!!.text = "" + act.securityGuard2Skill
        shipWeapons!!.text = "" + act.shipWeapons

        scienceOfficerStamina!!.text = "" + act.scienceOfficerStamina
        medicalOfficerStamina!!.text = "" + act.medicalOfficerStamina
        engineeringOfficerStamina!!.text = "" + act.engineeringOfficerStamina
        securityOfficerStamina!!.text = "" + act.securityOfficerStamina
        securityGuard1Stamina!!.text = "" + act.securityGuard1Stamina
        securityGuard2Stamina!!.text = "" + act.securityGuard2Stamina
        shipShields!!.text = "" + act.shipShields
    }
}
