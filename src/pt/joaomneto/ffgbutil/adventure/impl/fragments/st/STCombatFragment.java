package pt.joaomneto.ffgbutil.adventure.impl.fragments.st;

import java.util.ArrayList;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.STAdventure;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class STCombatFragment extends DialogFragment implements AdventureFragment {

	TextView combatResult = null;
	Button attackButton = null;
	Button addCombatButton = null;

	public STCombatFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_04st_adventure_combat, container, false);

		final STAdventure adv = (STAdventure) getActivity();

		combatResult = (TextView) rootView.findViewById(R.id.combatResult);
		attackButton = (Button) rootView.findViewById(R.id.attackButton);
		addCombatButton = (Button) rootView.findViewById(R.id.addCombatButton);

		final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

		final GridLayout grid = (GridLayout) rootView.findViewById(R.id.combatantGrid);

		// TEST CODE
		View combatantView = adv.getLayoutInflater().inflate(R.layout.component_04st_combatant, null);

		TextView crewCombatant = (TextView) combatantView.getRootView().findViewById(R.id.crewCombatant);
		TextView enemyCombatant = (TextView) combatantView.getRootView().findViewById(R.id.enemyCombatant);

		crewCombatant.setText("Engineering Officer");
		enemyCombatant.setText("Sk:18 St:22");

		combatantView.setBackgroundColor(Color.GREEN);
		grid.addView(combatantView);

		combatantView = adv.getLayoutInflater().inflate(R.layout.component_04st_combatant, null);

		crewCombatant = (TextView) combatantView.getRootView().findViewById(R.id.crewCombatant);
		enemyCombatant = (TextView) combatantView.getRootView().findViewById(R.id.enemyCombatant);

		crewCombatant.setText("Medical Officer");
		enemyCombatant.setText("Sk:14 St:23");

		combatantView.setBackgroundColor(Color.GREEN);
		grid.addView(combatantView);

		combatantView = adv.getLayoutInflater().inflate(R.layout.component_04st_combatant, null);

		crewCombatant = (TextView) combatantView.getRootView().findViewById(R.id.crewCombatant);
		enemyCombatant = (TextView) combatantView.getRootView().findViewById(R.id.enemyCombatant);

		crewCombatant.setText("Security Officer");
		enemyCombatant.setText("Sk:15 St:25");

		combatantView.setBackgroundColor(Color.GREEN);
		grid.addView(combatantView);

		combatantView = adv.getLayoutInflater().inflate(R.layout.component_04st_combatant, null);

		crewCombatant = (TextView) combatantView.getRootView().findViewById(R.id.crewCombatant);
		enemyCombatant = (TextView) combatantView.getRootView().findViewById(R.id.enemyCombatant);

		crewCombatant.setText("Engineering Officer");
		enemyCombatant.setText("Sk:16 St:26");

		combatantView.setBackgroundColor(Color.GREEN);
		grid.addView(combatantView);

		combatantView = adv.getLayoutInflater().inflate(R.layout.component_04st_combatant, null);

		crewCombatant = (TextView) combatantView.getRootView().findViewById(R.id.crewCombatant);
		enemyCombatant = (TextView) combatantView.getRootView().findViewById(R.id.enemyCombatant);

		crewCombatant.setText("Security Guard 1");
		enemyCombatant.setText("Sk:17 St:27");

		combatantView.setBackgroundColor(Color.GREEN);
		grid.addView(combatantView);

		addCombatButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(adv);

				final View addCombatantView = adv.getLayoutInflater().inflate(R.layout.component_04st_add_combatant,
						null);

				builder.setTitle("Add Combatants").setCancelable(false)
						.setNegativeButton("Close", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(), 0);
								dialog.cancel();
							}
						});

				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(), 0);

						final View combatantView = adv.getLayoutInflater().inflate(R.layout.component_04st_combatant,
								null);

						TextView crewCombatant = (TextView) combatantView.getRootView()
								.findViewById(R.id.crewCombatant);
						TextView enemyCombatant = (TextView) combatantView.getRootView().findViewById(
								R.id.enemyCombatant);

						EditText enemySkillValue = (EditText) addCombatantView.findViewById(R.id.enemySkillValue);
						EditText enemyStaminaValue = (EditText) addCombatantView.findViewById(R.id.enemyStaminaValue);
						Spinner crewmanSpinner = (Spinner) addCombatantView.findViewById(R.id.crewmanSpinner);

						crewCombatant.setText(crewmanSpinner.getSelectedItem().toString());
						enemyCombatant.setText("Sk:" + enemySkillValue.getText().toString() + " St:"
								+ enemyStaminaValue.getText().toString());

						grid.addView(combatantView);

					}
				});

				AlertDialog alert = builder.create();

				List<String> list = new ArrayList<String>();

				if (adv.isLandingPartyEngineeringOfficer())
					list.add(getResources().getString(R.string.engineeringOfficer));
				if (adv.isLandingPartyMedicalOfficer())
					list.add(getResources().getString(R.string.medicalOfficer));
				if (adv.isLandingPartyScienceOfficer())
					list.add(getResources().getString(R.string.scienceOfficer));
				if (adv.isLandingPartySecurityOfficer())
					list.add(getResources().getString(R.string.securityOfficer));
				if (adv.isLandingPartySecurityGuard1())
					list.add(getResources().getString(R.string.securityGuard1));
				if (adv.isLandingPartySecurityGuard2())
					list.add(getResources().getString(R.string.securityGuard2));

				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(adv, android.R.layout.simple_spinner_item,
						list);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				Spinner spinner = (Spinner) addCombatantView.findViewById(R.id.crewmanSpinner);
				spinner.setAdapter(dataAdapter);

				EditText skillValue = (EditText) addCombatantView.findViewById(R.id.enemySkillValue);

				alert.setView(addCombatantView);

				mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
				skillValue.requestFocus();

				alert.show();
			}
		});

		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {

	}
}
