package pt.joaomneto.ffgbutil.adventure.impl.fragments.st;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.STAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.STAdventure.STCrewman;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class STCombatFragment extends DialogFragment implements AdventureFragment {

	TextView combatResult = null;
	Button combatTurnButton = null;
	Button addCombatButton = null;
	Button resetButton = null;
	Switch combatTypeSwitch = null;
	View rootView = null;

	SparseArray<CombatPosition> combatPositions = new SparseArray<STCombatFragment.CombatPosition>();

	boolean phaserCombat = false;
	boolean defenseOnly = false;
	Set<STCrewman> inAttackCombat = new HashSet<STAdventure.STCrewman>();
	Set<Integer> finishedCombats = new HashSet<Integer>();
	int currentCombat = 0;
	int handicap = 0;

	boolean combatStarted = false;

	private static Integer[] gridRows;

	static {
		gridRows = new Integer[] { R.id.combat0, R.id.combat1, R.id.combat2, R.id.combat3, R.id.combat4, R.id.combat5 };
	}

	int row = 0;
	int maxRows = 6;

	public STCombatFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_04st_adventure_combat, container, false);

		combatResult = (TextView) rootView.findViewById(R.id.combatResult);
		combatTurnButton = (Button) rootView.findViewById(R.id.attackButton);
		addCombatButton = (Button) rootView.findViewById(R.id.addCombatButton);
		combatTypeSwitch = (Switch) rootView.findViewById(R.id.combatType);
		resetButton = (Button) rootView.findViewById(R.id.resetCombat);

		combatTypeSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				phaserCombat = isChecked;

			}
		});

		addCombatButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				addCombatButtonOnClick();
			}

		});

		resetButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				resetCombat();
				refreshScreensFromResume();
			}
		});

		combatTurnButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (combatPositions.size() == 0)
					return;

				if (combatStarted == false) {
					combatStarted = true;
					combatTypeSwitch.setClickable(false);
				}

				if (phaserCombat) {
					phaserCombatTurn();
				} else {
					handToHandCombatTurn();
				}
			}

		});

		refreshScreensFromResume();

		return rootView;
	}

	private void handToHandCombatTurn() {

		CombatPosition position = combatPositions.get(currentCombat);

		if (!finishedCombats.contains(currentCombat)) {
			STAdventure adv = (STAdventure) getActivity();
			int crewmanDiceRoll = DiceRoller.roll2D6();
			int crewmanSkill = adv.getCrewmanSkill(position.getCrewman());
			int crewmanAttackStrength = crewmanDiceRoll + crewmanSkill;
			int enemyDiceRoll = DiceRoller.roll2D6();
			int enemyAttackStrength = enemyDiceRoll + position.getCurrentSkill();
			LinearLayout row = (LinearLayout) rootView.findViewById(gridRows[currentCombat]);
			String crewmanString = adv.getStringForCrewman(position.getCrewman());
			if (crewmanAttackStrength > enemyAttackStrength) {
				if (!position.isDefenseOnly()) {
					position.setCurrentStamina(Math.max(0, position.getCurrentStamina() - 2));
					combatResult.setText("The " + crewmanString + " has hit his enemy! (" + crewmanDiceRoll + " + "
							+ crewmanSkill + ") vs (" + enemyDiceRoll + " + " + position.getCurrentSkill() + ")");
				} else {
					combatResult.setText("The " + crewmanString + " has blocked the enemy attack! (" + crewmanDiceRoll
							+ " + " + crewmanSkill + ") vs (" + enemyDiceRoll + " + " + position.getCurrentSkill()
							+ ")");
				}
			} else if (crewmanAttackStrength < enemyAttackStrength) {
				adv.setCrewmanStamina(position.getCrewman(),
						(Math.max(0, adv.getCrewmanStamina(position.getCrewman()) - 2)));
				combatResult.setText("The " + crewmanString + " has been hit... (" + crewmanDiceRoll + " + "
						+ crewmanSkill + ") vs (" + enemyDiceRoll + " + " + position.getCurrentSkill() + ")");
			} else {
				combatResult.setText("Both the " + crewmanString + " and his enemy have missed");
			}
			if (position.getCurrentStamina() == 0 || adv.getCrewmanStamina(position.getCrewman()) == 0) {
				removeCombatant(row);

				if (position.getCurrentStamina() == 0)
					combatResult.setText("The " + crewmanString + " has defeated his enemy!");

				if (adv.getCrewmanStamina(position.getCrewman()) == 0) {
					combatResult.setText("The " + crewmanString + " has died...");
					adv.setCrewmanDead(position.getCrewman());
					if (position.getCrewman().equals(STCrewman.CAPTAIN)) {
						adv.showAlert("You're dead...");
					}
				}
			}
		}
		currentCombat++;
		CombatPosition nextposition = combatPositions.get(currentCombat);

		while (nextposition == null && combatPositions.size() != 0) {
			currentCombat++;
			if (currentCombat == maxRows) {
				currentCombat = 0;
			}
			nextposition = combatPositions.get(currentCombat);
		}

		refreshScreensFromResume();

	}

	private void removeCombatant(LinearLayout row, int position) {
		row.removeAllViews();
		finishedCombats.add(position);
		CombatPosition toDelete = combatPositions.get(position);
		combatPositions.remove(position);

		for (int i = 0; i < maxRows; i++) {
			CombatPosition cp = combatPositions.get(i);
			if (cp != null && cp.getCrewman().equals(toDelete.getCrewman())) {
				cp.setDefenseOnly(false);
				break;
			}
		}
		if (combatPositions.size() == 0) {
			resetCombat();
		}
	}

	private void removeCombatant(LinearLayout row) {
		removeCombatant(row, currentCombat);
	}

	private void phaserCombatTurn() {

		if (!finishedCombats.contains(currentCombat)) {
			STAdventure adv = (STAdventure) getActivity();
			CombatPosition position = combatPositions.get(currentCombat);
			int crewmanDiceRoll = DiceRoller.roll2D6();
			int crewmanSkill = adv.getCrewmanSkill(position.getCrewman());
			int enemyDiceRoll = DiceRoller.roll2D6();

			int crewmanHandicap = 0;

			if (!adv.getStringForCrewman(position.getCrewman()).startsWith("Security")
					&& !position.getCrewman().equals(STCrewman.CAPTAIN)) {
				crewmanHandicap = -3;
			}

			LinearLayout row = (LinearLayout) rootView.findViewById(gridRows[currentCombat]);
			String crewmanString = adv.getStringForCrewman(position.getCrewman());

			if (crewmanDiceRoll < (crewmanSkill + crewmanHandicap + handicap)) {
				position.setCurrentStamina(0);
				combatResult.setText("The " + crewmanString + " has killed his enemy! (" + crewmanDiceRoll + ") > ("
						+ crewmanSkill + (crewmanHandicap != 0 ? (" + " + crewmanHandicap) : "")
						+ (handicap != 0 ? (" + " + handicap) : "") + ")");
				removeCombatant(row);

			} else {
				combatResult.setText("The " + crewmanString + " has missed his shot!");
			}
			if (position.getCurrentStamina() > 0) {
				if (enemyDiceRoll < position.getCurrentSkill()) {
					if (!position.isDefenseOnly()) {
						adv.setCrewmanDead(position.getCrewman());
						combatResult.setText(combatResult.getText() + "\nThe " + crewmanString + " has died... ("
								+ enemyDiceRoll + ") > (" + position.getCurrentSkill() + ")");
						removeCombatant(row);
					} else {
						int killedCrewman = new Random(System.currentTimeMillis()).nextInt(combatPositions.size() - 1);

						while (finishedCombats.contains(killedCrewman)) {
							killedCrewman = new Random(System.currentTimeMillis()).nextInt(combatPositions.size() - 1);
						}

						STCrewman killedCrewmanObj = combatPositions.get(killedCrewman).getCrewman();
						adv.setCrewmanDead(killedCrewmanObj);
						combatResult.setText(combatResult.getText() + "\nThe "
								+ adv.getStringForCrewman(killedCrewmanObj) + " was killed by (Sk:"
								+ position.getCurrentSkill() + " St:" + position.getCurrentStamina() + ")... ("
								+ enemyDiceRoll + ") > (" + position.getCurrentSkill() + ")");
						for (int i = 0; i < maxRows; i++) {
							CombatPosition combat = combatPositions.get(i);
							if (combat != null && combat.getCrewman().equals(killedCrewmanObj)) {
								LinearLayout toClear = (LinearLayout) rootView.findViewById(gridRows[i]);
								removeCombatant(toClear, i);
							}
						}
					}
				} else {
					combatResult.setText(combatResult.getText() + "\nThe enemy has missed his shot!");
				}
			}
		}
		currentCombat++;
		CombatPosition nextposition = combatPositions.get(currentCombat);

		while (nextposition == null && combatPositions.size() != 0) {
			currentCombat++;
			if (currentCombat == maxRows) {
				currentCombat = 0;
			}
			nextposition = combatPositions.get(currentCombat);
		}

		refreshScreensFromResume();

	}

	private void addCombatButtonOnClick() {

		STAdventure adv = (STAdventure) getActivity();

		final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (combatStarted)
			return;

		if (row >= maxRows) {
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(adv);

		final View addCombatantView = adv.getLayoutInflater().inflate(R.layout.component_04st_add_combatant, null);

		builder.setTitle("Add Combatants").setCancelable(false)
				.setNegativeButton("Close", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(), 0);
						dialog.cancel();
					}
				});

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				int currentRow = getNextRow();

				mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(), 0);

				EditText enemySkillValue = (EditText) addCombatantView.findViewById(R.id.enemySkillValue);
				EditText enemyStaminaValue = (EditText) addCombatantView.findViewById(R.id.enemyStaminaValue);
				Spinner crewmanSpinner = (Spinner) addCombatantView.findViewById(R.id.crewmanSpinner);

				Integer skill = Integer.valueOf(enemySkillValue.getText().toString());
				Integer stamina = Integer.valueOf(enemyStaminaValue.getText().toString());

				addCombatant(rootView, currentRow, crewmanSpinner, skill, stamina, defenseOnly);

			}

		});

		AlertDialog alert = builder.create();

		ArrayAdapter<String> dataAdapter = getCrewmanSpinnerAdapter();

		Spinner spinner = (Spinner) addCombatantView.findViewById(R.id.crewmanSpinner);
		spinner.setAdapter(dataAdapter);

		EditText skillValue = (EditText) addCombatantView.findViewById(R.id.enemySkillValue);

		alert.setView(addCombatantView);

		mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
		skillValue.requestFocus();

		alert.show();
	}

	private ArrayAdapter<String> getCrewmanSpinnerAdapter() {

		STAdventure adv = (STAdventure) getActivity();

		List<String> list = new ArrayList<String>();

		if (!inAttackCombat.contains(STCrewman.CAPTAIN) || defenseOnly)
			list.add(getResources().getString(R.string.captain));

		if (adv.isLandingPartyEngineeringOfficer()
				&& (!inAttackCombat.contains(STCrewman.ENGINEERING_OFFICER) || defenseOnly))
			list.add(getResources().getString(R.string.engineeringOfficer));
		if (adv.isLandingPartyMedicalOfficer() && (!inAttackCombat.contains(STCrewman.MEDICAL_OFFICER) || defenseOnly))
			list.add(getResources().getString(R.string.medicalOfficer));
		if (adv.isLandingPartyScienceOfficer() && (!inAttackCombat.contains(STCrewman.SCIENCE_OFFICER) || defenseOnly))
			list.add(getResources().getString(R.string.scienceOfficer));
		if (adv.isLandingPartySecurityOfficer()
				&& (!inAttackCombat.contains(STCrewman.SECURITY_OFFICER) || defenseOnly))
			list.add(getResources().getString(R.string.securityOfficer));
		if (adv.isLandingPartySecurityGuard1() && (!inAttackCombat.contains(STCrewman.SECURITY_GUARD1) || defenseOnly))
			list.add(getResources().getString(R.string.securityGuard1));
		if (adv.isLandingPartySecurityGuard2() && (!inAttackCombat.contains(STCrewman.SECURITY_GUARD2) || defenseOnly))
			list.add(getResources().getString(R.string.securityGuard2));

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(adv, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return dataAdapter;
	}

	private void addCombatant(final View rootView, int currentRow, Spinner crewmanSpinner, Integer skill,
			Integer stamina, boolean defenseOnly) {
		STAdventure adv = (STAdventure) getActivity();

		final View combatantView = adv.getLayoutInflater().inflate(R.layout.component_04st_combatant, null);

		TextView tv = (TextView) combatantView.getRootView().findViewById(R.id.combatText);

		if (combatPositions.size() == 0) {

			RadioButton radio = (RadioButton) combatantView.getRootView().findViewById(R.id.combatSelected);
			radio.setChecked(true);
		}

		LinearLayout grid = (LinearLayout) rootView.findViewById(gridRows[currentRow]);

		CombatPosition combatPosition = new CombatPosition(adv.getCrewmanForString(crewmanSpinner.getSelectedItem()
				.toString()), stamina, skill, defenseOnly);

		combatPositions.put(currentRow, combatPosition);
		inAttackCombat.add(combatPosition.getCrewman());

		if (adv.getLandingParty().size() == inAttackCombat.size()) {
			this.defenseOnly = true;
		}

		tv.setText(combatPosition.toGridString());

		grid.addView(combatantView);
	}

	@Override
	public void refreshScreensFromResume() {
		for (int i = 0; i < maxRows; i++) {
			LinearLayout ll = (LinearLayout) rootView.findViewById(gridRows[i]);
			RadioButton combatSelected = (RadioButton) ll.findViewById(R.id.combatSelected);
			TextView combatText = (TextView) ll.findViewById(R.id.combatText);

			if (combatSelected != null)
				combatSelected.setChecked(i == currentCombat);

			if (combatText != null)
				combatText.setText(combatPositions.get(i).toGridString());
		}
	}

	private int getNextRow() {
		return row++;
	}

	private class CombatPosition {

		STAdventure.STCrewman crewman;
		Integer currentStamina;
		Integer currentSkill;
		boolean defenseOnly;

		public CombatPosition(STCrewman crewman, Integer stamina, Integer skill, boolean defenseOnly) {
			this.crewman = crewman;
			this.currentStamina = stamina;
			this.currentSkill = skill;
			this.defenseOnly = defenseOnly;
		}

		public CharSequence toGridString() {
			return ((STAdventure) getActivity()).getStringForCrewman(crewman) + " vs. Sk:" + currentSkill + " St:"
					+ currentStamina;
		}

		public STAdventure.STCrewman getCrewman() {
			return crewman;
		}

		public Integer getCurrentStamina() {
			return currentStamina;
		}

		public Integer getCurrentSkill() {
			return currentSkill;
		}

		public boolean isDefenseOnly() {
			return defenseOnly;
		}

		public void setCurrentStamina(Integer currentStamina) {
			this.currentStamina = currentStamina;
		}

		public void setDefenseOnly(boolean defenseOnly) {
			this.defenseOnly = defenseOnly;
		}

	}

	private void resetCombat() {

		combatPositions.clear();
		inAttackCombat.clear();
		finishedCombats.clear();
		phaserCombat = false;
		defenseOnly = false;
		combatStarted = false;
		row = 0;

		combatTypeSwitch.setClickable(true);

		for (Integer rowId : gridRows) {
			LinearLayout gridRow = (LinearLayout) rootView.findViewById(rowId);
			gridRow.removeAllViews();
		}
		refreshScreensFromResume();
	}
}
