package pt.joaomneto.ffgbutil.adventure.impl.fragments;

import java.util.HashSet;
import java.util.Set;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Switch;
import android.widget.TextView;

public class AdventureCombatFragment extends DialogFragment implements
		AdventureFragment {

	TextView combatResult = null;
	Button combatTurnButton = null;
	Button startCombatButton = null;
	Button addCombatButton = null;
	Button testLuckButton = null;
	Button resetButton = null;
	Switch combatTypeSwitch = null;
	View rootView = null;

	SparseArray<Combatant> combatPositions = new SparseArray<AdventureCombatFragment.Combatant>();

	boolean sequenceCombat = false;
	Set<Integer> finishedCombats = new HashSet<Integer>();
	int currentCombat = 0;
	int previousCombat = 0;
	int handicap = 0;

	boolean draw = false;
	boolean luckTest = false;
	boolean hit = false;

	boolean combatStarted = false;

	private static Integer[] gridRows;

	static {
		gridRows = new Integer[] { R.id.combat0, R.id.combat1, R.id.combat2,
				R.id.combat3, R.id.combat4, R.id.combat5 };
	}

	int row = 0;
	int maxRows = 6;

	public AdventureCombatFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_adventure_combat,
				container, false);

		combatResult = (TextView) rootView.findViewById(R.id.combatResult);
		combatTurnButton = (Button) rootView.findViewById(R.id.attackButton);
		startCombatButton = (Button) rootView.findViewById(R.id.startCombat);
		addCombatButton = (Button) rootView.findViewById(R.id.addCombatButton);
		combatTypeSwitch = (Switch) rootView.findViewById(R.id.combatType);
		resetButton = (Button) rootView.findViewById(R.id.resetCombat);
		testLuckButton = (Button) rootView.findViewById(R.id.testLuckButton);

		combatTypeSwitch
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						sequenceCombat = isChecked;

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

				if (sequenceCombat) {
					sequenceCombatTurn();
				} else {
					standardCombatTurn();
				}
			}

		});

		// final Button addCombatButtonF = addCombatButton;

		startCombatButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (combatPositions.size() == 0)
					return;

				if (combatStarted == false) {
					combatStarted = true;
					combatTypeSwitch.setClickable(false);
				}

				if (sequenceCombat) {
					sequenceCombatTurn();
				} else {
					standardCombatTurn();
				}

				addCombatButton.setVisibility(View.GONE);
				combatTypeSwitch.setVisibility(View.GONE);
				startCombatButton.setVisibility(View.GONE);
				testLuckButton.setVisibility(View.VISIBLE);
				combatTurnButton.setVisibility(View.VISIBLE);

				RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);

				p.addRule(RelativeLayout.ABOVE, R.id.attackButton);
				p.addRule(RelativeLayout.LEFT_OF, R.id.resetCombat);

				testLuckButton.setLayoutParams(p);

				p = new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);

				p.addRule(RelativeLayout.ABOVE, R.id.attackButton);
				p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				//
				resetButton.setLayoutParams(p);

			}

		});

		testLuckButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Adventure adv = (Adventure) getActivity();

				if (draw || luckTest)
					return;
				luckTest = true;
				boolean result = adv.testLuckInternal();
				if (result) {
					combatResult.setText("You're lucky!");
					if (hit) {
						Combatant combatant = combatPositions
								.get(previousCombat);
						combatant.setCurrentStamina(combatant
								.getCurrentStamina() - 1);
						int enemyStamina = combatant.getCurrentStamina();
						if (enemyStamina <= 0) {
							enemyStamina = 0;
							adv.showAlert("You've defeated your opponent!");
							LinearLayout row = (LinearLayout) rootView
									.findViewById(gridRows[previousCombat]);
							removeCombatant(row, previousCombat);
							advanceCombat();
						}
					} else {
						adv.setCurrentStamina(adv.getCurrentStamina() + 1);
					}
				} else {
					combatResult.setText("You're unlucky...");
					if (hit) {
						Combatant combatant = combatPositions
								.get(previousCombat);
						combatant.setCurrentStamina(combatant
								.getCurrentStamina() + 1);

					} else {
						adv.setCurrentStamina(adv.getCurrentStamina() - 1);
					}
					if (adv.getCurrentStamina() == 0) {
						adv.showAlert("You're dead...");
					}
				}
				refreshScreensFromResume();
			}
		});

		refreshScreensFromResume();

		return rootView;
	}

	private void sequenceCombatTurn() {

		Combatant position = combatPositions.get(currentCombat);

		if (!finishedCombats.contains(currentCombat)) {
			draw = false;
			luckTest = false;
			hit = false;
			Adventure adv = (Adventure) getActivity();
			int diceRoll = DiceRoller.roll2D6();
			int skill = adv.getCurrentSkill();
			int attackStrength = diceRoll + skill + position.getHandicap();
			int enemyDiceRoll = DiceRoller.roll2D6();
			int enemyAttackStrength = enemyDiceRoll
					+ position.getCurrentSkill();
			LinearLayout row = (LinearLayout) rootView
					.findViewById(gridRows[currentCombat]);
			if (attackStrength > enemyAttackStrength) {
				if (!position.isDefenseOnly()) {
					position.setCurrentStamina(Math.max(0,
							position.getCurrentStamina() - 2));
					hit = true;
					combatResult.setText("You have hit the enemy! ("
							+ diceRoll
							+ " + "
							+ skill
							+ (position.getHandicap() >= 0 ? (" + " + position
									.getHandicap()) : "") + ") vs ("
							+ enemyDiceRoll + " + "
							+ position.getCurrentSkill() + ")");
				} else {
					draw = true;
					combatResult.setText("You have blocked the enemy attack! ("
							+ diceRoll
							+ " + "
							+ skill
							+ (position.getHandicap() >= 0 ? (" + " + position
									.getHandicap()) : "") + ") vs ("
							+ enemyDiceRoll + " + "
							+ position.getCurrentSkill() + ")");
				}
			} else if (attackStrength < enemyAttackStrength) {
				adv.setCurrentStamina((Math.max(0, adv.getCurrentStamina() - 2)));
				combatResult.setText("Youve have been hit... ("
						+ diceRoll
						+ " + "
						+ skill
						+ (position.getHandicap() >= 0 ? (" + " + position
								.getHandicap()) : "") + ") vs ("
						+ enemyDiceRoll + " + " + position.getCurrentSkill()
						+ ")");
			} else {

				combatResult.setText("Both you and the enemy have missed");
				draw = true;
			}

			if (position.getCurrentStamina() == 0) {
				removeCombatant(row);
				combatResult.setText("You have defeated an enemy!");

			}

			if (adv.getCurrentStamina() == 0) {
				combatResult.setText("You have died...");
			}
		}
		if (combatPositions.size() > 0) {
			advanceCombat();
		} else {
			resetCombat();
		}

		refreshScreensFromResume();

	}

	private void advanceCombat() {
		previousCombat = currentCombat;
		currentCombat++;
		Combatant nextposition = combatPositions.get(currentCombat);

		while (nextposition == null && combatPositions.size() != 0) {
			currentCombat++;
			if (currentCombat == maxRows) {
				currentCombat = 0;
			}
			nextposition = combatPositions.get(currentCombat);
		}
	}

	private void removeCombatant(LinearLayout row, int position) {
		row.removeAllViews();
		finishedCombats.add(position);
		combatPositions.remove(position);

		for (int i = 0; i < maxRows; i++) {
			Combatant cp = combatPositions.get(i);
			if (cp != null) {
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

	private void standardCombatTurn() {
		Combatant position = combatPositions.get(currentCombat);

		if (!finishedCombats.contains(currentCombat)) {
			draw = false;
			luckTest = false;
			hit = false;
			Adventure adv = (Adventure) getActivity();
			int diceRoll = DiceRoller.roll2D6();
			int skill = adv.getCurrentSkill();
			int attackStrength = diceRoll + skill + position.getHandicap();
			int enemyDiceRoll = DiceRoller.roll2D6();
			int enemyAttackStrength = enemyDiceRoll
					+ position.getCurrentSkill();
			LinearLayout row = (LinearLayout) rootView
					.findViewById(gridRows[currentCombat]);
			if (attackStrength > enemyAttackStrength) {

				position.setCurrentStamina(Math.max(0,
						position.getCurrentStamina() - 2));
				hit = true;
				combatResult.setText("You have hit the enemy! ("
						+ diceRoll
						+ " + "
						+ skill
						+ (position.getHandicap() >= 0 ? (" + " + position
								.getHandicap()) : "") + ") vs ("
						+ enemyDiceRoll + " + " + position.getCurrentSkill()
						+ ")");

			} else if (attackStrength < enemyAttackStrength) {
				adv.setCurrentStamina((Math.max(0, adv.getCurrentStamina() - 2)));
				combatResult.setText("Youve have been hit... ("
						+ diceRoll
						+ " + "
						+ skill
						+ (position.getHandicap() >= 0 ? (" + " + position
								.getHandicap()) : "") + ") vs ("
						+ enemyDiceRoll + " + " + position.getCurrentSkill()
						+ ")");
			} else {

				combatResult.setText("Both you and the enemy have missed");
				draw = true;
			}

			if (position.getCurrentStamina() == 0) {
				removeCombatant(row);
				combatResult.setText("You have defeated an enemy!");
				advanceCombat();

			}

			if (adv.getCurrentStamina() == 0) {
				combatResult.setText("You have died...");
			}
		}
		if (combatPositions.size() == 0) {
			resetCombat();
		}

		refreshScreensFromResume();

	}

	private void addCombatButtonOnClick() {

		Adventure adv = (Adventure) getActivity();

		final InputMethodManager mgr = (InputMethodManager) adv
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (combatStarted)
			return;

		if (row >= maxRows) {
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(adv);

		final View addCombatantView = adv.getLayoutInflater().inflate(
				R.layout.component_add_combatant, null);

		builder.setTitle("Add Enemy")
				.setCancelable(false)
				.setNegativeButton("Close",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								mgr.hideSoftInputFromWindow(
										addCombatantView.getWindowToken(), 0);
								dialog.cancel();
							}
						});

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				int currentRow = getNextRow();

				mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(),
						0);

				EditText enemySkillValue = (EditText) addCombatantView
						.findViewById(R.id.enemySkillValue);
				EditText enemyStaminaValue = (EditText) addCombatantView
						.findViewById(R.id.enemyStaminaValue);
				EditText handicapValue = (EditText) addCombatantView
						.findViewById(R.id.handicapValue);

				Integer skill = Integer.valueOf(enemySkillValue.getText()
						.toString());
				Integer stamina = Integer.valueOf(enemyStaminaValue.getText()
						.toString());
				Integer handicap = Integer.valueOf(handicapValue.getText()
						.toString());

				addCombatant(rootView, currentRow, skill, stamina, handicap);

			}

		});

		AlertDialog alert = builder.create();

		EditText skillValue = (EditText) addCombatantView
				.findViewById(R.id.enemySkillValue);

		alert.setView(addCombatantView);

		mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
		skillValue.requestFocus();

		alert.show();
	}

	private void addCombatant(final View rootView, int currentRow,
			Integer skill, Integer stamina, Integer handicap) {
		Adventure adv = (Adventure) getActivity();

		final View combatantView = adv.getLayoutInflater().inflate(
				R.layout.component_combatant, null);

		TextView tv = (TextView) combatantView.getRootView().findViewById(
				R.id.combatText);

		if (combatPositions.size() == 0) {

			RadioButton radio = (RadioButton) combatantView.getRootView()
					.findViewById(R.id.combatSelected);
			radio.setChecked(true);
		}

		LinearLayout grid = (LinearLayout) rootView
				.findViewById(gridRows[currentRow]);

		Combatant combatPosition = new Combatant(stamina, skill, handicap,
				combatPositions.size() > 0);

		combatPositions.put(currentRow, combatPosition);

		tv.setText(combatPosition.toGridString());

		grid.addView(combatantView);
	}

	@Override
	public void refreshScreensFromResume() {
		for (int i = 0; i < maxRows; i++) {
			LinearLayout ll = (LinearLayout) rootView.findViewById(gridRows[i]);
			RadioButton combatSelected = (RadioButton) ll
					.findViewById(R.id.combatSelected);
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

	private class Combatant {

		Integer currentStamina;
		Integer currentSkill;
		Integer handicap;
		boolean defenseOnly;

		public Combatant(Integer stamina, Integer skill, Integer handicap,
				boolean defenseOnly) {
			this.currentStamina = stamina;
			this.currentSkill = skill;
			this.handicap = handicap;
			this.defenseOnly = defenseOnly;
		}

		public CharSequence toGridString() {
			return ("Skill:" + currentSkill + " Stamina:" + currentStamina);
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

		public Integer getHandicap() {
			return handicap;
		}

		public void setHandicap(Integer handicap) {
			this.handicap = handicap;
		}

	}

	private void resetCombat() {

		combatPositions.clear();
		finishedCombats.clear();
		sequenceCombat = false;
		combatStarted = false;
		row = 0;
		currentCombat = 0;

		combatTypeSwitch.setClickable(true);
		sequenceCombat = combatTypeSwitch.isChecked();

		for (Integer rowId : gridRows) {
			LinearLayout gridRow = (LinearLayout) rootView.findViewById(rowId);
			gridRow.removeAllViews();
		}

		addCombatButton.setVisibility(View.VISIBLE);
		combatTypeSwitch.setVisibility(View.VISIBLE);
		startCombatButton.setVisibility(View.VISIBLE);
		testLuckButton.setVisibility(View.GONE);
		combatTurnButton.setVisibility(View.GONE);

		LayoutParams p = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		p.addRule(RelativeLayout.BELOW, R.id.addCombatButton);
		p.addRule(RelativeLayout.ALIGN_RIGHT, R.id.combatType);
		p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		resetButton.setLayoutParams(p);

		refreshScreensFromResume();
	}
}
