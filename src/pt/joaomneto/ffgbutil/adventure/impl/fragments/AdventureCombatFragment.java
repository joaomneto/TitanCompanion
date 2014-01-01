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

	protected TextView combatResult = null;
	protected Button combatTurnButton = null;
	protected Button startCombatButton = null;
	protected Button addCombatButton = null;
	protected Button testLuckButton = null;
	protected Button resetButton = null;
	protected Switch combatTypeSwitch = null;
	protected View rootView = null;

	protected SparseArray<Combatant> combatPositions = new SparseArray<AdventureCombatFragment.Combatant>();

	public static final String NORMAL = "NORMAL";
	public static final String SEQUENCE = "SEQUENCE";

	public String offText = "Normal";
	public String onText = "Sequence";

	protected String combatMode = NORMAL;
	protected Set<Integer> finishedCombats = new HashSet<Integer>();
	protected int currentCombat = 0;
	protected int previousCombat = 0;
	protected int handicap = 0;

	protected boolean draw = false;
	protected boolean luckTest = false;
	protected boolean hit = false;

	protected boolean combatStarted = false;

	int staminaLoss = 0;

	protected static Integer[] gridRows;

	static {
		gridRows = new Integer[] { R.id.combat0, R.id.combat1, R.id.combat2,
				R.id.combat3, R.id.combat4, R.id.combat5 };
	}

	protected int row = 0;
	protected int maxRows = 6;

	public AdventureCombatFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_adventure_combat,
				container, false);

		init();

		return rootView;
	}

	protected void combatTurn() {
		if (combatPositions.size() == 0)
			return;

		if (combatStarted == false) {
			combatStarted = true;
			combatTypeSwitch.setClickable(false);
		}

		if (combatMode.equals(SEQUENCE)) {
			sequenceCombatTurn();
		} else {
			standardCombatTurn();
		}
	}

	protected void switchLayoutCombatStarted() {
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

	public String getOfftext() {
		return "Normal";
	}

	public String getOntext() {
		return "Sequence";
	}

	protected void init() {
		combatResult = (TextView) rootView.findViewById(R.id.combatResult);
		combatTurnButton = (Button) rootView.findViewById(R.id.attackButton);
		startCombatButton = (Button) rootView.findViewById(R.id.startCombat);
		addCombatButton = (Button) rootView.findViewById(R.id.addCombatButton);
		combatTypeSwitch = (Switch) rootView.findViewById(R.id.combatType);
		resetButton = (Button) rootView.findViewById(R.id.resetCombat);
		testLuckButton = (Button) rootView.findViewById(R.id.testLuckButton);

		combatTypeSwitch.setTextOff(getOfftext());
		combatTypeSwitch.setTextOn(getOntext());
		combatTypeSwitch
				.setOnCheckedChangeListener(new CombatTypeSwitchChangeListener());

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

				combatTurn();
			}

		});

		startCombatButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startCombat();

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
						combatant.setStaminaLoss(combatant.getStaminaLoss() + 1);
						int enemyStamina = combatant.getCurrentStamina();
						if (enemyStamina <= 0
								|| (getKnockoutStamina() != null && staminaLoss >= getKnockoutStamina())) {
							enemyStamina = 0;
							adv.showAlert("You've defeated your opponent!");
							LinearLayout row = (LinearLayout) rootView
									.findViewById(gridRows[previousCombat]);
							removeCombatant(row, previousCombat);
							advanceCombat();
						}
					} else {
						adv.setCurrentStamina(adv.getCurrentStamina() + 1);
						staminaLoss--;
					}
				} else {
					combatResult.setText("You're unlucky...");
					if (hit) {
						Combatant combatant = combatPositions
								.get(previousCombat);
						combatant.setCurrentStamina(combatant
								.getCurrentStamina() + 1);
						combatant.setStaminaLoss(combatant.getStaminaLoss() - 1);

					} else {
						adv.setCurrentStamina(adv.getCurrentStamina() - 1);
						staminaLoss++;
					}

					if (getKnockoutStamina() != null
							&& adv.getCurrentStamina() <= getKnockoutStamina()) {
						adv.showAlert("You've been knocked out...");
					}

					if (adv.getCurrentStamina() == 0) {
						adv.showAlert("You're dead...");
					}
				}
				refreshScreensFromResume();
			}
		});

		refreshScreensFromResume();
	}

	protected Integer getKnockoutStamina() {
		return null;
	}

	protected void sequenceCombatTurn() {

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
							position.getCurrentStamina() - getDamage()));
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
				adv.setCurrentStamina((Math.max(0, adv.getCurrentStamina() - convertDamageStringToInteger(position.getDamage()))));
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

	protected void advanceCombat() {
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

	protected void setFirstCombat() {
		currentCombat = 0;
		Combatant nextposition = combatPositions.get(currentCombat);

		while (nextposition == null && combatPositions.size() != 0) {
			currentCombat++;
			if (currentCombat == maxRows) {
				currentCombat = 0;
			}
			nextposition = combatPositions.get(currentCombat);
		}
	}

	protected void removeCombatant(LinearLayout row, int position) {
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

	protected void removeCombatant(LinearLayout row) {
		removeCombatant(row, currentCombat);
	}

	protected void standardCombatTurn() {
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

				int damage = getDamage();

				position.setCurrentStamina(Math.max(0,
						position.getCurrentStamina() - getDamage()));
				position.setStaminaLoss(position.getStaminaLoss() - damage);
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
				int damage = convertDamageStringToInteger(position.getDamage());
				staminaLoss += damage;
				adv.setCurrentStamina((Math.max(0, adv.getCurrentStamina()
						- damage)));
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

			if (position.getCurrentStamina() == 0
					|| (getKnockoutStamina() != null && staminaLoss >= getKnockoutStamina())) {
				removeCombatant(row);
				combatResult.setText("You have defeated an enemy!");
				advanceCombat();

			}

			if (getKnockoutStamina() != null
					&& staminaLoss >= getKnockoutStamina()) {
				adv.showAlert("You've been knocked out...");
			}

			if (adv.getCurrentStamina() == 0) {
				adv.showAlert("You're dead...");
			}
		}
		if (combatPositions.size() == 0) {
			resetCombat();
		}

		refreshScreensFromResume();

	}

	protected void addCombatButtonOnClick() {

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

				addCombatant(rootView, currentRow, skill, stamina, handicap,
						"2");

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

	protected void addCombatant(final View rootView, int currentRow,
			Integer skill, Integer stamina, Integer handicap, String damage) {
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
				combatPositions.size() > 0, damage);

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

	protected int getNextRow() {
		return row++;
	}

	protected int getDamage() {
		return 2;
	}

	protected class Combatant {

		Integer currentStamina;
		Integer currentSkill;
		Integer handicap;
		String damage;
		boolean defenseOnly;
		Integer staminaLoss = 0;

		public Combatant(Integer stamina, Integer skill, Integer handicap,
				boolean defenseOnly, String damage) {
			this.currentStamina = stamina;
			this.currentSkill = skill;
			this.handicap = handicap;
			this.defenseOnly = defenseOnly;
			this.damage = damage;
		}

		public Combatant(Integer stamina, Integer skill, Integer handicap,
				boolean defenseOnly) {
			this(stamina, skill, handicap, defenseOnly, "2");
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

		public String getDamage() {
			return damage;
		}

		public Integer getStaminaLoss() {
			return staminaLoss;
		}

		public void setStaminaLoss(Integer staminaLoss) {
			this.staminaLoss = staminaLoss;
		}

		public void setDamage(String damage) {
			this.damage = damage;
		}
	}

	protected void resetCombat() {

		staminaLoss = 0;

		combatPositions.clear();
		finishedCombats.clear();
		combatMode = NORMAL;
		combatStarted = false;
		row = 0;
		currentCombat = 0;

		combatTypeSwitch.setClickable(true);
		combatMode = combatTypeSwitch.isChecked() ? SEQUENCE : NORMAL;

		for (Integer rowId : gridRows) {
			LinearLayout gridRow = (LinearLayout) rootView.findViewById(rowId);
			gridRow.removeAllViews();
		}

		switchLayoutReset();

		refreshScreensFromResume();
	}

	protected void switchLayoutReset() {
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
	}

	protected String combatTypeSwitchBehaviour(boolean isChecked) {
		return combatMode = isChecked ? SEQUENCE : NORMAL;
	}

	private class CombatTypeSwitchChangeListener implements
			OnCheckedChangeListener {

		// @Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			combatTypeSwitchBehaviour(isChecked);

		}

	}

	protected static int convertDamageStringToInteger(String damage) {
		if (damage.equals("1D6")) {
			return DiceRoller.rollD6();
		} else {
			return Integer.parseInt(damage);
		}
	}
	
	protected void startCombat() {
		combatTurn();

		switchLayoutCombatStarted();
	}
}
