package pt.joaomneto.ffgbutil.adventure.impl.fragments.st;

import java.util.ArrayList;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.STAdventure;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class STCombatFragment extends DialogFragment implements AdventureFragment {

	TextView combatResult = null;
	Button attackButton = null;
	Button addCombatButton = null;
	
	private static Integer[] gridRows;
	
	static{
		gridRows = new Integer[]{
			R.id.combat1,
			R.id.combat2,
			R.id.combat3,
			R.id.combat4,
			R.id.combat5,
			R.id.combat6
		};
	}
	
	int row = 0;
	int maxRows = 6;

	public STCombatFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View rootView = inflater.inflate(R.layout.fragment_04st_adventure_combat, container, false);

		final STAdventure adv = (STAdventure) getActivity();

		combatResult = (TextView) rootView.findViewById(R.id.combatResult);
		attackButton = (Button) rootView.findViewById(R.id.attackButton);
		addCombatButton = (Button) rootView.findViewById(R.id.addCombatButton);
		

		final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);


		// TEST CODE

		LinearLayout grid = (LinearLayout) rootView.findViewById(gridRows[row++]);
		
		
		RelativeLayout combatantView = (RelativeLayout) adv.getLayoutInflater().inflate(R.layout.component_04st_combatant, null);
		TextView tv = (TextView) combatantView.findViewById(R.id.combatText);
		tv.setText("Engineering Officer vs. Sk:18 St:22");
		grid.addView(combatantView);
		
		
		
		grid = (LinearLayout) rootView.findViewById(gridRows[row++]);

		combatantView = (RelativeLayout) adv.getLayoutInflater().inflate(R.layout.component_04st_combatant, null);
		tv = (TextView) combatantView.findViewById(R.id.combatText);
		tv.setText("Medical Officer vs. Sk:14 St:23");
		grid.addView(combatantView);
		
		
		
		
		grid = (LinearLayout) rootView.findViewById(gridRows[row++]);

		combatantView = (RelativeLayout) adv.getLayoutInflater().inflate(R.layout.component_04st_combatant, null);
		tv = (TextView) combatantView.findViewById(R.id.combatText);
		tv.setText("Security Officer vs. Sk:15 St:25");
		grid.addView(combatantView);
		
		
		
		
		grid = (LinearLayout) rootView.findViewById(gridRows[row++]);

		combatantView = (RelativeLayout) adv.getLayoutInflater().inflate(R.layout.component_04st_combatant, null);
		tv = (TextView) combatantView.findViewById(R.id.combatText);
		tv.setText("Engineering Officer vs. Sk:17 St:27");
		grid.addView(combatantView);
		
		
		
		
		grid = (LinearLayout) rootView.findViewById(gridRows[row++]);

		combatantView = (RelativeLayout) adv.getLayoutInflater().inflate(R.layout.component_04st_combatant, null);
		tv = (TextView) combatantView.findViewById(R.id.combatText);
		tv.setText("Security Guard 1 vs. Sk:19 St:29");
		grid.addView(combatantView);
		
		
	
		addCombatButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(row >= maxRows){
					return;
				}
				
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
						
						LinearLayout grid = (LinearLayout) rootView.findViewById(gridRows[getNextRow()]);
						
						mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(), 0);

						final View combatantView = adv.getLayoutInflater().inflate(R.layout.component_04st_combatant,
								null);

						TextView tv = (TextView) combatantView.getRootView()
								.findViewById(R.id.combatText);

						EditText enemySkillValue = (EditText) addCombatantView.findViewById(R.id.enemySkillValue);
						EditText enemyStaminaValue = (EditText) addCombatantView.findViewById(R.id.enemyStaminaValue);
						Spinner crewmanSpinner = (Spinner) addCombatantView.findViewById(R.id.crewmanSpinner);

						tv.setText(crewmanSpinner.getSelectedItem().toString() + " vs. Sk:" + enemySkillValue.getText().toString() + " St:"
								+ enemyStaminaValue.getText().toString());

						grid.addView(combatantView);

					}
				});

				AlertDialog alert = builder.create();

				List<String> list = new ArrayList<String>();
				
				list.add(getResources().getString(R.string.captain));

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
	


	private int getNextRow() {
		return row++;
	}
}
