package pt.joaomneto.ffgbutil.adventure.impl.fragments.sa;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.SAAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.RelativeLayout.LayoutParams;

public class SAAdventureCombatFragment extends AdventureCombatFragment
		implements AdventureFragment {

	public static final String SA12_GUNFIGHT = "SA12_GUNFIGHT";

	Button grenadeButton = null;

	boolean assaultBlaster = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_12sa_adventure_combat,
				container, false);

		grenadeButton = (Button) rootView.findViewById(R.id.grenadeButton);

		grenadeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SAAdventure adv = (SAAdventure) getActivity();

				int idx = adv.getWeapons().indexOf("Grenade");

				if (idx < 0)
					return;
				else{
					adv.getWeapons().remove(idx);
					adv.getVitalStatsFragment().refreshScreensFromResume();
				}

				String result = "";

				for (int i = 0; i < 6; i++) {
					Combatant enemy = combatPositions.get(i);
					LinearLayout row = (LinearLayout) rootView
							.findViewById(gridRows[i]);
					if (enemy != null && enemy.getCurrentStamina() > 0) {
						int damage = DiceRoller.rollD6();
						enemy.setCurrentStamina(Math.max(
								enemy.getCurrentStamina() - damage, 0));
						String text = "The enemy (SK: "
								+ enemy.getCurrentSkill() + " ST: "
								+ enemy.getCurrentStamina()
								+ ") has been hit (-" + damage + " ST)";
						if (result.isEmpty()) {
							result = text;
						} else {
							result += "\n" + text;
						}

						if (enemy.getCurrentStamina() == 0) {
							removeCombatant(row);

						}
						combatResult.setText(result);
						refreshScreensFromResume();
					}

				}

			}
		});

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

		if (combatMode.equals(SA12_GUNFIGHT)) {
			gunfightCombatTurn();
		} else {
			standardCombatTurn();
		}
	}

	private void gunfightCombatTurn() {

		setFirstCombat();
		Combatant position = combatPositions.get(currentCombat);
		SAAdventure adv = (SAAdventure) getActivity();

		assaultBlaster = adv.getWeapons().contains("Assault Blaster");

		if (!finishedCombats.contains(currentCombat)) {
			draw = false;
			luckTest = false;
			hit = false;
			int diceRoll = DiceRoller.roll2D6();
			int skill = adv.getCurrentSkill();
			boolean hitEnemy = diceRoll <= skill;
			LinearLayout row = (LinearLayout) rootView
					.findViewById(gridRows[currentCombat]);
			if (hitEnemy) {
				int damage = assaultBlaster ? DiceRoller.rollD6() : 2;
				position.setCurrentStamina(Math.max(0,
						position.getCurrentStamina() - damage));
				hit = true;
				combatResult.setText("You have hit the enemy! (-" + damage
						+ " ST)");
			} else {
				draw = true;
				combatResult.setText("You have missed the enemy...");
			}

			if (position.getCurrentStamina() == 0) {
				removeCombatant(row);
				combatResult.setText(combatResult.getText()
						+ "\nYou have defeated an enemy!");

			}

			for (int i = 0; i < 6; i++) {
				Combatant enemy = combatPositions.get(i);
				if (enemy != null && enemy.getCurrentStamina() > 0) {
					if (DiceRoller.roll2D6() <= enemy.getCurrentSkill()) {
						int damage = enemy.getDamage().equals("2") ? 2
								: DiceRoller.rollD6();
						combatResult.setText(combatResult.getText()
								+ "\nAn enemy (SK: " + enemy.getCurrentSkill()
								+ " ST: " + enemy.getCurrentStamina()
								+ ") has hit you.(-" + damage + " ST)");
						adv.setCurrentStamina(Math.max(0,
								adv.getCurrentStamina() - damage));
					} else {
						combatResult.setText(combatResult.getText()
								+ "\nAn enemy (SK: " + enemy.getCurrentSkill()
								+ " ST: " + enemy.getCurrentStamina()
								+ ") has missed!");
					}
				}
			}

			if (adv.getCurrentStamina() <= 0) {
				combatResult.setText("You have died...");
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

		final View addCombatantView = adv
				.getLayoutInflater()
				.inflate(
						combatMode == null || combatMode.equals(NORMAL) ? R.layout.component_add_combatant
								: R.layout.component_12sa_add_combatant, null);

		String[] allWeapons = { "Electric Lash", "Assault Blaster" };

		final Spinner weaponSpinner = (Spinner) addCombatantView
				.findViewById(R.id.weaponSpinner);
		if (weaponSpinner != null) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(adv,
					android.R.layout.simple_list_item_1, android.R.id.text1,
					allWeapons);
			weaponSpinner.setAdapter(adapter);
		}

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

				addCombatant(
						rootView,
						currentRow,
						skill,
						stamina,
						handicap,
						weaponSpinner == null ? null : weaponSpinner
								.getSelectedItem().toString()
								.equals("Assault Blaster") ? "1d6" : "2");

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

	protected String combatTypeSwitchBehaviour(boolean isChecked) {
		return combatMode = isChecked ? SA12_GUNFIGHT : NORMAL;
	}
	
	protected void switchLayoutCombatStarted() {
		grenadeButton.setVisibility(View.INVISIBLE);
		super.switchLayoutCombatStarted();
		
	}
	
	protected void switchLayoutReset() {
		grenadeButton.setVisibility(View.VISIBLE);
		super.switchLayoutReset();
	}

}
