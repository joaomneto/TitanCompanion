package pt.joaomneto.ffgbutil.adventure.impl.fragments.st;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.STAdventure;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class STCrewStatsFragment extends Fragment implements AdventureFragment {
	
	private TextView scienceOfficerSkillValue = null;
	private TextView scienceOfficerStaminaValue = null;
	private Button scienceOfficerMinusSkillButton = null;
	private Button scienceOfficerPlusSkillButton = null;
	private Button scienceOfficerMinusStaminaButton = null;
	private Button scienceOfficerPlusStaminaButton = null;

	private TextView medicalOfficerSkillValue = null;
	private TextView medicalOfficerStaminaValue = null;
	private Button medicalOfficerMinusSkillButton = null;
	private Button medicalOfficerPlusSkillButton = null;
	private Button medicalOfficerMinusStaminaButton = null;
	private Button medicalOfficerPlusStaminaButton = null;

	private TextView engineeringOfficerSkillValue = null;
	private TextView engineeringOfficerStaminaValue = null;
	private Button engineeringOfficerMinusSkillButton = null;
	private Button engineeringOfficerPlusSkillButton = null;
	private Button engineeringOfficerMinusStaminaButton = null;
	private Button engineeringOfficerPlusStaminaButton = null;

	private TextView securityOfficerSkillValue = null;
	private TextView securityOfficerStaminaValue = null;
	private Button securityOfficerMinusSkillButton = null;
	private Button securityOfficerPlusSkillButton = null;
	private Button securityOfficerMinusStaminaButton = null;
	private Button securityOfficerPlusStaminaButton = null;

	private TextView securityGuard1SkillValue = null;
	private TextView securityGuard1StaminaValue = null;
	private Button securityGuard1MinusSkillButton = null;
	private Button securityGuard1PlusSkillButton = null;
	private Button securityGuard1MinusStaminaButton = null;
	private Button securityGuard1PlusStaminaButton = null;

	private TextView securityGuard2SkillValue = null;
	private TextView securityGuard2StaminaValue = null;
	private Button securityGuard2MinusSkillButton = null;
	private Button securityGuard2PlusSkillButton = null;
	private Button securityGuard2MinusStaminaButton = null;
	private Button securityGuard2PlusStaminaButton = null;

	private CheckBox scienceOfficerLandingParty = null;
	private CheckBox medicalOfficerLandingParty = null;
	private CheckBox engineeringOfficerLandingParty = null;
	private CheckBox securityOfficerLandingParty = null;
	private CheckBox securityGuard1LandingParty = null;
	private CheckBox securityGuard2LandingParty = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_04st_adventure_crewvitalstats, container, false);

		final STAdventure adv = (STAdventure) getActivity();

		scienceOfficerSkillValue = (TextView) rootView.findViewById(R.id.scienceOfficerSkillValue);
		scienceOfficerStaminaValue = (TextView) rootView.findViewById(R.id.scienceOfficerStaminaValue);
		scienceOfficerMinusSkillButton = (Button) rootView.findViewById(R.id.scienceOfficerMinusSkillButton);
		scienceOfficerPlusSkillButton = (Button) rootView.findViewById(R.id.scienceOfficerPlusSkillButton);
		scienceOfficerMinusStaminaButton = (Button) rootView.findViewById(R.id.scienceOfficerMinusStaminaButton);
		scienceOfficerPlusStaminaButton = (Button) rootView.findViewById(R.id.scienceOfficerPlusStaminaButton);

		medicalOfficerSkillValue = (TextView) rootView.findViewById(R.id.medicalOfficerSkillValue);
		medicalOfficerStaminaValue = (TextView) rootView.findViewById(R.id.medicalOfficerStaminaValue);
		medicalOfficerMinusSkillButton = (Button) rootView.findViewById(R.id.medicalOfficerMinusSkillButton);
		medicalOfficerPlusSkillButton = (Button) rootView.findViewById(R.id.medicalOfficerPlusSkillButton);
		medicalOfficerMinusStaminaButton = (Button) rootView.findViewById(R.id.medicalOfficerMinusStaminaButton);
		medicalOfficerPlusStaminaButton = (Button) rootView.findViewById(R.id.medicalOfficerPlusStaminaButton);

		engineeringOfficerSkillValue = (TextView) rootView.findViewById(R.id.engineeringOfficerSkillValue);
		engineeringOfficerStaminaValue = (TextView) rootView.findViewById(R.id.engineeringOfficerStaminaValue);
		engineeringOfficerMinusSkillButton = (Button) rootView.findViewById(R.id.engineeringOfficerMinusSkillButton);
		engineeringOfficerPlusSkillButton = (Button) rootView.findViewById(R.id.engineeringOfficerPlusSkillButton);
		engineeringOfficerMinusStaminaButton = (Button) rootView
				.findViewById(R.id.engineeringOfficerMinusStaminaButton);
		engineeringOfficerPlusStaminaButton = (Button) rootView.findViewById(R.id.engineeringOfficerPlusStaminaButton);

		securityOfficerSkillValue = (TextView) rootView.findViewById(R.id.securityOfficerSkillValue);
		securityOfficerStaminaValue = (TextView) rootView.findViewById(R.id.securityOfficerStaminaValue);
		securityOfficerMinusSkillButton = (Button) rootView.findViewById(R.id.securityOfficerMinusSkillButton);
		securityOfficerPlusSkillButton = (Button) rootView.findViewById(R.id.securityOfficerPlusSkillButton);
		securityOfficerMinusStaminaButton = (Button) rootView.findViewById(R.id.securityOfficerMinusStaminaButton);
		securityOfficerPlusStaminaButton = (Button) rootView.findViewById(R.id.securityOfficerPlusStaminaButton);

		securityGuard1SkillValue = (TextView) rootView.findViewById(R.id.securityGuard1SkillValue);
		securityGuard1StaminaValue = (TextView) rootView.findViewById(R.id.securityGuard1StaminaValue);
		securityGuard1MinusSkillButton = (Button) rootView.findViewById(R.id.securityGuard1MinusSkillButton);
		securityGuard1PlusSkillButton = (Button) rootView.findViewById(R.id.securityGuard1PlusSkillButton);
		securityGuard1MinusStaminaButton = (Button) rootView.findViewById(R.id.securityGuard1MinusStaminaButton);
		securityGuard1PlusStaminaButton = (Button) rootView.findViewById(R.id.securityGuard1PlusStaminaButton);

		securityGuard2SkillValue = (TextView) rootView.findViewById(R.id.securityGuard2SkillValue);
		securityGuard2StaminaValue = (TextView) rootView.findViewById(R.id.securityGuard2StaminaValue);
		securityGuard2MinusSkillButton = (Button) rootView.findViewById(R.id.securityGuard2MinusSkillButton);
		securityGuard2PlusSkillButton = (Button) rootView.findViewById(R.id.securityGuard2PlusSkillButton);
		securityGuard2MinusStaminaButton = (Button) rootView.findViewById(R.id.securityGuard2MinusStaminaButton);
		securityGuard2PlusStaminaButton = (Button) rootView.findViewById(R.id.securityGuard2PlusStaminaButton);

		scienceOfficerLandingParty = (CheckBox) rootView.findViewById(R.id.scienceOfficerLandingParty);
		medicalOfficerLandingParty = (CheckBox) rootView.findViewById(R.id.medicalOfficerLandingParty);
		engineeringOfficerLandingParty = (CheckBox) rootView.findViewById(R.id.engineeringOfficerLandingParty);
		securityOfficerLandingParty = (CheckBox) rootView.findViewById(R.id.securityOfficerLandingParty);
		securityGuard1LandingParty = (CheckBox) rootView.findViewById(R.id.securityGuard1LandingParty);
		securityGuard2LandingParty = (CheckBox) rootView.findViewById(R.id.securityGuard2LandingParty);

		scienceOfficerPlusSkillButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentScienceOfficerSkill() < adv.getInitialScienceOfficerSkill())
					adv.setCurrentScienceOfficerSkill(adv.getCurrentScienceOfficerSkill() + 1);
				refreshScreensFromResume();
			}
		});
		medicalOfficerPlusSkillButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentMedicalOfficerSkill() < adv.getInitialMedicalOfficerSkill())
					adv.setCurrentMedicalOfficerSkill(adv.getCurrentMedicalOfficerSkill() + 1);
				refreshScreensFromResume();
			}
		});
		engineeringOfficerPlusSkillButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentEngineeringOfficerSkill() < adv.getInitialEngineeringOfficerSkill())
					adv.setCurrentEngineeringOfficerSkill(adv.getCurrentEngineeringOfficerSkill() + 1);
				refreshScreensFromResume();
			}
		});
		securityOfficerPlusSkillButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentSecurityOfficerSkill() < adv.getInitialSecurityOfficerSkill())
					adv.setCurrentSecurityOfficerSkill(adv.getCurrentSecurityOfficerSkill() + 1);
				refreshScreensFromResume();
			}
		});
		securityGuard1PlusSkillButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentSecurityGuard1Skill() < adv.getInitialSecurityGuard1Skill())
					adv.setCurrentSecurityGuard1Skill(adv.getCurrentSecurityGuard1Skill() + 1);
				refreshScreensFromResume();
			}
		});
		securityGuard2PlusSkillButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentSecurityGuard2Skill() < adv.getInitialSecurityGuard2Skill())
					adv.setCurrentSecurityGuard2Skill(adv.getCurrentSecurityGuard2Skill() + 1);
				refreshScreensFromResume();
			}
		});

		scienceOfficerMinusSkillButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentScienceOfficerSkill() > 0)
					adv.setCurrentScienceOfficerSkill(adv.getCurrentScienceOfficerSkill() - 1);
				refreshScreensFromResume();
			}
		});
		medicalOfficerMinusSkillButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentMedicalOfficerSkill() > 0)
					adv.setCurrentMedicalOfficerSkill(adv.getCurrentMedicalOfficerSkill() - 1);
				refreshScreensFromResume();
			}
		});
		engineeringOfficerMinusSkillButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentEngineeringOfficerSkill() > 0)
					adv.setCurrentEngineeringOfficerSkill(adv.getCurrentEngineeringOfficerSkill() - 1);
				refreshScreensFromResume();
			}
		});
		securityOfficerMinusSkillButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentSecurityOfficerSkill() > 0)
					adv.setCurrentSecurityOfficerSkill(adv.getCurrentSecurityOfficerSkill() - 1);
				refreshScreensFromResume();
			}
		});
		securityGuard1MinusSkillButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentSecurityGuard1Skill() > 0)
					adv.setCurrentSecurityGuard1Skill(adv.getCurrentSecurityGuard1Skill() - 1);
				refreshScreensFromResume();
			}
		});
		securityGuard2MinusSkillButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentSecurityGuard2Skill() > 0)
					adv.setCurrentSecurityGuard2Skill(adv.getCurrentSecurityGuard2Skill() - 1);
				refreshScreensFromResume();
			}
		});
		scienceOfficerMinusStaminaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentScienceOfficerStamina() > 0)
					adv.setCurrentScienceOfficerStamina(adv.getCurrentScienceOfficerStamina() - 1);
				refreshScreensFromResume();
			}
		});
		scienceOfficerPlusStaminaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentScienceOfficerStamina() < adv.getInitialScienceOfficerStamina())
					adv.setCurrentScienceOfficerStamina(adv.getCurrentScienceOfficerStamina() + 1);
				refreshScreensFromResume();
			}
		});
		medicalOfficerMinusStaminaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentMedicalOfficerStamina() > 0)
					adv.setCurrentMedicalOfficerStamina(adv.getCurrentMedicalOfficerStamina() - 1);
				refreshScreensFromResume();
			}
		});
		medicalOfficerPlusStaminaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentMedicalOfficerStamina() < adv.getInitialMedicalOfficerStamina())
					adv.setCurrentMedicalOfficerStamina(adv.getCurrentMedicalOfficerStamina() + 1);
				refreshScreensFromResume();
			}
		});
		engineeringOfficerMinusStaminaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentEngineeringOfficerStamina() > 0)
					adv.setCurrentEngineeringOfficerStamina(adv.getCurrentEngineeringOfficerStamina() - 1);
				refreshScreensFromResume();
			}
		});
		engineeringOfficerPlusStaminaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentEngineeringOfficerStamina() < adv.getInitialEngineeringOfficerStamina())
					adv.setCurrentEngineeringOfficerStamina(adv.getCurrentEngineeringOfficerStamina() + 1);
				refreshScreensFromResume();
			}
		});
		securityOfficerMinusStaminaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentSecurityOfficerStamina() > 0)
					adv.setCurrentSecurityOfficerStamina(adv.getCurrentSecurityOfficerStamina() - 1);
				refreshScreensFromResume();
			}
		});
		securityOfficerPlusStaminaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentSecurityOfficerStamina() < adv.getInitialSecurityOfficerStamina())
					adv.setCurrentSecurityOfficerStamina(adv.getCurrentSecurityOfficerStamina() + 1);
				refreshScreensFromResume();
			}
		});
		securityGuard1MinusStaminaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentSecurityGuard1Stamina() > 0)
					adv.setCurrentSecurityGuard1Stamina(adv.getCurrentSecurityGuard1Stamina() - 1);
				refreshScreensFromResume();
			}
		});
		securityGuard1PlusStaminaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentSecurityGuard1Stamina() < adv.getInitialSecurityGuard1Stamina())
					adv.setCurrentSecurityGuard1Stamina(adv.getCurrentSecurityGuard1Stamina() + 1);
				refreshScreensFromResume();
			}
		});
		securityGuard2MinusStaminaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentSecurityGuard2Stamina() > 0)
					adv.setCurrentSecurityGuard2Stamina(adv.getCurrentSecurityGuard2Stamina() - 1);
				refreshScreensFromResume();
			}
		});
		securityGuard2PlusStaminaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adv.getCurrentSecurityGuard2Stamina() < adv.getInitialSecurityGuard2Stamina())
					adv.setCurrentSecurityGuard2Stamina(adv.getCurrentSecurityGuard2Stamina() + 1);
				refreshScreensFromResume();
			}
		});

		scienceOfficerLandingParty.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (adv.isDeadScienceOfficer())
					return;
				adv.setLandingPartyScienceOfficer(isChecked);
			}
		});
		medicalOfficerLandingParty.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (adv.isDeadMedicalOfficer())
					return;
				adv.setLandingPartyMedicalOfficer(isChecked);
			}
		});
		engineeringOfficerLandingParty.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (adv.isDeadEngineeringOfficer())
					return;
				adv.setLandingPartyEngineeringOfficer(isChecked);
			}
		});
		securityOfficerLandingParty.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (adv.isDeadSecurityOfficer())
					return;
				adv.setLandingPartySecurityOfficer(isChecked);
			}
		});
		securityGuard1LandingParty.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (adv.isDeadSecurityGuard1())
					return;
				adv.setLandingPartySecurityGuard1(isChecked);
			}
		});
		securityGuard2LandingParty.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (adv.isDeadSecurityGuard2())
					return;
				adv.setLandingPartySecurityGuard2(isChecked);
			}
		});

		Button restoreStaminaLandingPartyButton = (Button) rootView.findViewById(R.id.restoreStaminaLandingPartyButton);

		restoreStaminaLandingPartyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.isLandingPartyEngineeringOfficer()) {
					adv.setCurrentEngineeringOfficerStamina(Math.min(adv.getInitialEngineeringOfficerStamina(),
							adv.getCurrentEngineeringOfficerStamina() + 2));
				}
				if (adv.isLandingPartyScienceOfficer()) {
					adv.setCurrentScienceOfficerStamina(Math.min(adv.getInitialScienceOfficerStamina(),
							adv.getCurrentScienceOfficerStamina() + 2));
				}
				if (adv.isLandingPartyMedicalOfficer()) {
					adv.setCurrentMedicalOfficerStamina(Math.min(adv.getInitialMedicalOfficerStamina(),
							adv.getCurrentMedicalOfficerStamina() + 2));
				}
				if (adv.isLandingPartySecurityOfficer()) {
					adv.setCurrentSecurityOfficerStamina(Math.min(adv.getInitialSecurityOfficerStamina(),
							adv.getCurrentSecurityOfficerStamina() + 2));
				}
				if (adv.isLandingPartySecurityGuard1()) {
					adv.setCurrentSecurityGuard1Stamina(Math.min(adv.getInitialSecurityGuard1Stamina(),
							adv.getCurrentSecurityGuard1Stamina() + 2));
				}
				if (adv.isLandingPartySecurityGuard2()) {
					adv.setCurrentSecurityGuard2Stamina(Math.min(adv.getInitialSecurityGuard2Stamina(),
							adv.getCurrentSecurityGuard2Stamina() + 2));
				}

				adv.setCurrentStamina(Math.min(adv.getInitialStamina(), adv.getCurrentStamina() + 2));

				refreshScreensFromResume();

			}
		});

		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {

		STAdventure adv = (STAdventure) getActivity();

		scienceOfficerSkillValue.setText("" + adv.getCurrentScienceOfficerSkill());
		scienceOfficerStaminaValue.setText("" + adv.getCurrentScienceOfficerStamina());
		medicalOfficerSkillValue.setText("" + adv.getCurrentMedicalOfficerSkill());
		medicalOfficerStaminaValue.setText("" + adv.getCurrentMedicalOfficerStamina());
		engineeringOfficerSkillValue.setText("" + adv.getCurrentEngineeringOfficerSkill());
		engineeringOfficerStaminaValue.setText("" + adv.getCurrentEngineeringOfficerStamina());
		securityOfficerSkillValue.setText("" + adv.getCurrentSecurityOfficerSkill());
		securityOfficerStaminaValue.setText("" + adv.getCurrentSecurityOfficerStamina());
		securityGuard1SkillValue.setText("" + adv.getCurrentSecurityGuard1Skill());
		securityGuard1StaminaValue.setText("" + adv.getCurrentSecurityGuard1Stamina());
		securityGuard2SkillValue.setText("" + adv.getCurrentSecurityGuard2Skill());
		securityGuard2StaminaValue.setText("" + adv.getCurrentSecurityGuard2Stamina());

		scienceOfficerLandingParty.setChecked(adv.isLandingPartyScienceOfficer());
		medicalOfficerLandingParty.setChecked(adv.isLandingPartyMedicalOfficer());
		engineeringOfficerLandingParty.setChecked(adv.isLandingPartyEngineeringOfficer());
		securityOfficerLandingParty.setChecked(adv.isLandingPartySecurityOfficer());
		securityGuard1LandingParty.setChecked(adv.isLandingPartySecurityGuard1());
		securityGuard2LandingParty.setChecked(adv.isLandingPartySecurityGuard2());

	}

}
