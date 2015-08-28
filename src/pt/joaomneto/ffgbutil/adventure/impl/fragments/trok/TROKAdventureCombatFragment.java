package pt.joaomneto.ffgbutil.adventure.impl.fragments.trok;

import java.util.Arrays;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.ffgbutil.adventure.impl.util.DiceRoll;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TROKAdventureCombatFragment extends AdventureCombatFragment {

	public static final String TROK15_GUNFIGHT = "TROK15_GUNFIGHT";

	private Spinner damageSpinner = null;
	private TextView damageText = null;

	private List<String> damageList = Arrays.asList(new String[] { "4", "5", "6", "1D6" });

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_15trok_adventure_combat, container, false);

		damageSpinner = (Spinner) rootView.findViewById(R.id.damageSpinner);
		damageText = (TextView) rootView.findViewById(R.id.damageText);

		if (damageSpinner != null) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, damageList);
			damageSpinner.setAdapter(adapter);
		}

		if (overrideDamage != null) {
			damageSpinner.setSelection(damageList.indexOf(overrideDamage));
		} else {
			damageSpinner.setSelection(0);
		}

		damageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				overrideDamage = damageList.get(arg2);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				overrideDamage = null;

			}
		});

		init();

		return rootView;
	}

	String overrideDamage = null;

	protected void combatTurn() {
		if (combatPositions.size() == 0)
			return;

		if (combatStarted == false) {
			combatStarted = true;
			combatTypeSwitch.setClickable(false);
		}
		if (combatMode.equals(TROK15_GUNFIGHT)) {
			gunfightCombatTurn();
		} else {
			standardCombatTurn();
		}

	}

	@Override
	protected int getDamage() {
		if (combatMode.equals(NORMAL)) {
				return 2;
		} else if (combatMode.equals(TROK15_GUNFIGHT)) {
			return convertDamageStringToInteger(overrideDamage);
		}

		return 2;
	}

	protected String combatTypeSwitchBehaviour(boolean isChecked) {
		return combatMode = isChecked ? TROK15_GUNFIGHT : NORMAL;
	}

	public String getOntext() {
		return "Gunfight";
	}

	@Override
	protected void resetCombat() {
		super.resetCombat();
	}

	protected void switchLayoutCombatStarted() {

		damageSpinner.setVisibility(View.GONE);
		damageText.setVisibility(View.GONE);

		super.switchLayoutCombatStarted();
	}

	protected void switchLayoutReset() {
		damageSpinner.setVisibility(View.VISIBLE);
		damageText.setVisibility(View.VISIBLE);

		super.switchLayoutReset();
	}

	protected Integer getKnockoutStamina() {
		return null;
	}

	protected void addCombatButtonOnClick() {

		Adventure adv = (Adventure) getActivity();

		final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (combatStarted)
			return;


		AlertDialog.Builder builder = new AlertDialog.Builder(adv);

		final View addCombatantView = adv.getLayoutInflater().inflate(
				combatMode == null || combatMode.equals(NORMAL) ? R.layout.component_add_combatant : R.layout.component_add_combatant_damage, null);

		final EditText damageValue = (EditText) addCombatantView.findViewById(R.id.enemyDamage);
		if(damageValue!=null)
			damageValue.setText(getDefaultEnemyDamage());

		builder.setTitle("Add Enemy").setCancelable(false).setNegativeButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(), 0);
				dialog.cancel();
			}
		});

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				

				mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(), 0);

				EditText enemySkillValue = (EditText) addCombatantView.findViewById(R.id.enemySkillValue);
				EditText enemyStaminaValue = (EditText) addCombatantView.findViewById(R.id.enemyStaminaValue);
				EditText handicapValue = (EditText) addCombatantView.findViewById(R.id.handicapValue);

				Integer skill = Integer.valueOf(enemySkillValue.getText().toString());
				Integer stamina = Integer.valueOf(enemyStaminaValue.getText().toString());
				Integer handicap = Integer.valueOf(handicapValue.getText().toString());

				addCombatant(rootView, skill, stamina, handicap, damageValue == null ? getDefaultEnemyDamage() : damageValue.getText().toString());

			}

		});

		AlertDialog alert = builder.create();

		EditText skillValue = (EditText) addCombatantView.findViewById(R.id.enemySkillValue);

		alert.setView(addCombatantView);

		mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
		skillValue.requestFocus();

		alert.show();
	}

	@Override
	protected void startCombat() {
		if (combatMode.equals(TROK15_GUNFIGHT)) {
			for (int i = 0; i < combatPositions.size(); i++) {
				Combatant c = combatPositions.get(i);
				if (c != null)
					c.setDamage("4");
			}
		}
		super.startCombat();
	}
	
	protected String getDefaultEnemyDamage(){
		return "4";
	}
	
	protected void gunfightCombatTurn() {

		Combatant position = getCurrentEnemy();
		Adventure adv = (Adventure) getActivity();

		// if (!finishedCombats.contains(currentCombat)) {
		draw = false;
		luckTest = false;
		hit = false;
		DiceRoll diceRoll = DiceRoller.roll2D6();
		int skill = adv.getCombatSkillValue();
		boolean hitEnemy = diceRoll.getSum() <= skill;
		if (hitEnemy) {
			int damage = getDamage();
			position.setCurrentStamina(Math.max(0, position.getCurrentStamina() - damage));
			hit = true;
			combatResult.setText("You have hit the enemy! (-" + damage + " ST)");
		} else {
			draw = true;
			combatResult.setText("You have missed the enemy...");
		}

		if (position.getCurrentStamina() == 0) {
			removeAndAdvanceCombat(position);
			combatResult.setText(combatResult.getText() + "\nYou have defeated an enemy!");

		}

		for (int i = 0; i < 6; i++) {
			Combatant enemy = combatPositions.get(i);
			if (enemy != null && enemy.getCurrentStamina() > 0) {
				if (DiceRoller.roll2D6().getSum() <= enemy.getCurrentSkill()) {
					int damage = convertDamageStringToInteger(enemy.getDamage());
					combatResult.setText(combatResult.getText() + "\nAn enemy (SK: " + enemy.getCurrentSkill() + " ST: " + enemy.getCurrentStamina()
							+ ") has hit you.(-" + damage + " ST)");
					adv.setCurrentStamina(Math.max(0, adv.getCurrentStamina() - damage));
				} else {
					combatResult.setText(combatResult.getText() + "\nAn enemy (SK: " + enemy.getCurrentSkill() + " ST: " + enemy.getCurrentStamina()
							+ ") has missed!");
				}
			}
		}

		if (adv.getCurrentStamina() <= 0) {
			combatResult.setText("You have died...");
		}

		refreshScreensFromResume();
	}

}
