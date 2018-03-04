package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.st;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.impl.STAdventureCreation;

public class STCrewAndShipVitalStatisticsFragment extends Fragment {

    TextView scienceOfficerSkill = null;
    TextView medicalOfficerSkill = null;
    TextView engineeringOfficerSkill = null;
    TextView securityOfficerSkill = null;
    TextView securityGuard1Skill = null;
    TextView securityGuard2Skill = null;
    TextView shipWeapons = null;

    TextView scienceOfficerStamina = null;
    TextView medicalOfficerStamina = null;
    TextView engineeringOfficerStamina = null;
    TextView securityOfficerStamina = null;
    TextView securityGuard1Stamina = null;
    TextView securityGuard2Stamina = null;
    TextView shipShields = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(
                R.layout.fragment_04st_adventurecreation_crewshipvitalstats,
                container, false);

        scienceOfficerSkill = rootView
                .findViewById(R.id.scienceOfficerSkillValue);
        medicalOfficerSkill = rootView
                .findViewById(R.id.medicalOfficerSkillValue);
        engineeringOfficerSkill = rootView
                .findViewById(R.id.engineeringOfficerSkillValue);
        securityOfficerSkill = rootView
                .findViewById(R.id.securityOfficerSkillValue);
        securityGuard1Skill = rootView
                .findViewById(R.id.securityGuard1SkillValue);
        securityGuard2Skill = rootView
                .findViewById(R.id.securityGuard2SkillValue);
        shipWeapons = rootView.findViewById(R.id.shipWeaponsValue);

        scienceOfficerStamina = rootView
                .findViewById(R.id.scienceOfficerStaminaValue);
        medicalOfficerStamina = rootView
                .findViewById(R.id.medicalOfficerStaminaValue);
        engineeringOfficerStamina = rootView
                .findViewById(R.id.engineeringOfficerStaminaValue);
        securityOfficerStamina = rootView
                .findViewById(R.id.securityOfficerStaminaValue);
        securityGuard1Stamina = rootView
                .findViewById(R.id.securityGuard1StaminaValue);
        securityGuard2Stamina = rootView
                .findViewById(R.id.securityGuard2StaminaValue);
        shipShields = rootView.findViewById(R.id.shipShieldsValue);

        return rootView;
    }

    public void updateFields() {
        STAdventureCreation act = (STAdventureCreation) getActivity();

        scienceOfficerSkill.setText("" + act.getScienceOfficerSkill());
        medicalOfficerSkill.setText("" + act.getMedicalOfficerSkill());
        engineeringOfficerSkill.setText("" + act.getEngineeringOfficerSkill());
        securityOfficerSkill.setText("" + act.getSecurityOfficerSkill());
        securityGuard1Skill.setText("" + act.getSecurityGuard1Skill());
        securityGuard2Skill.setText("" + act.getSecurityGuard2Skill());
        shipWeapons.setText("" + act.getShipWeapons());

        scienceOfficerStamina.setText("" + act.getScienceOfficerStamina());
        medicalOfficerStamina.setText("" + act.getMedicalOfficerStamina());
        engineeringOfficerStamina.setText("" + act.getEngineeringOfficerStamina());
        securityOfficerStamina.setText("" + act.getSecurityOfficerStamina());
        securityGuard1Stamina.setText("" + act.getSecurityGuard1Stamina());
        securityGuard2Stamina.setText("" + act.getSecurityGuard2Stamina());
        shipShields.setText("" + act.getShipShields());

    }

}
