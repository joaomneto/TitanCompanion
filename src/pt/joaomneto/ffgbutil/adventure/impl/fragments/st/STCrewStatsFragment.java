package pt.joaomneto.ffgbutil.adventure.impl.fragments.st;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.impl.STAdventure;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class STCrewStatsFragment extends Fragment {

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

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_04st_adventure_crewvitalstats, container, false);

		STAdventure adv = (STAdventure) getActivity();
		
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
		engineeringOfficerMinusStaminaButton = (Button) rootView.findViewById(R.id.engineeringOfficerMinusStaminaButton);
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

		return rootView;
	}

}
