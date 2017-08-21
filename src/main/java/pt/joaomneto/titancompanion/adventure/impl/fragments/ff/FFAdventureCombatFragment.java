package pt.joaomneto.titancompanion.adventure.impl.fragments.ff;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
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

import java.util.Arrays;
import java.util.List;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.titancompanion.util.DiceRoller;

public class FFAdventureCombatFragment extends AdventureCombatFragment{

	public static final String FF13_GUNFIGHT = "FF13_GUNFIGHT";
	String overrideDamage = null;
	private Spinner damageSpinner = null;
	private TextView damageText = null;
	private List<String> damageList = Arrays.asList("1", "2",
			"3", "4", "1D6");

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_13ff_adventure_combat,
				container, false);

		damageSpinner = rootView.findViewById(R.id.damageSpinner);
		damageText = rootView.findViewById(R.id.damageText);

		if (damageSpinner != null) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_list_item_1,
					android.R.id.text1, damageList);
			damageSpinner.setAdapter(adapter);
		}

		if (overrideDamage != null) {
			damageSpinner.setSelection(damageList.indexOf(overrideDamage));
		} else {
			damageSpinner.setSelection(0);
		}

		damageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
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

	protected void combatTurn() {
		if (combatPositions.size() == 0)
			return;

		if (combatStarted == false) {
			combatStarted = true;
			combatTypeSwitch.setClickable(false);
		}
		if (combatMode.equals(NORMAL)) {
			standardCombatTurn();
		} else {
			sequenceCombatTurn();
		}


		if (combatPositions.isEmpty()) {
			resetCombat();
		}
	}

	@Override
	protected int getDamage() {
		if (combatMode.equals(NORMAL)) {
			if (overrideDamage == null) {
				return 1;
			} else
				return convertDamageStringToInteger(overrideDamage);
		} else if (combatMode.equals(FF13_GUNFIGHT)) {
			return DiceRoller.rollD6();
		}

		return 2;
	}

	protected String combatTypeSwitchBehaviour(boolean isChecked) {
		return combatMode = isChecked ? FF13_GUNFIGHT : NORMAL;
	}

	public String getOntext() {
		return getString(R.string.gunfight);
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
		return 6;
	}

	protected void addCombatButtonOnClick() {

		Adventure adv = (Adventure) getActivity();

		final InputMethodManager mgr = (InputMethodManager) adv
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (combatStarted)
			return;


		AlertDialog.Builder builder = new AlertDialog.Builder(adv);

		final View addCombatantView = adv
				.getLayoutInflater()
				.inflate(
						combatMode == null || combatMode.equals(FF13_GUNFIGHT) ? R.layout.component_add_combatant
								: R.layout.component_add_combatant_damage, null);

		final EditText damageValue = addCombatantView
				.findViewById(R.id.enemyDamage);

		if (damageValue != null) {
			damageValue.setRawInputType(Configuration.KEYBOARD_12KEY);
		}

		builder.setTitle(R.string.addEnemy)
				.setCancelable(false)
				.setNegativeButton(R.string.close,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								mgr.hideSoftInputFromWindow(
										addCombatantView.getWindowToken(), 0);
								dialog.cancel();
							}
						});

		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				
				mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(),
						0);

				EditText enemySkillValue = addCombatantView
						.findViewById(R.id.enemySkillValue);
				EditText enemyStaminaValue = addCombatantView
						.findViewById(R.id.enemyStaminaValue);
				EditText handicapValue = addCombatantView
						.findViewById(R.id.handicapValue);

				Integer skill = Integer.valueOf(enemySkillValue.getText()
						.toString());
				Integer stamina = Integer.valueOf(enemyStaminaValue.getText()
						.toString());
				Integer handicap = Integer.valueOf(handicapValue.getText()
						.toString());

				addCombatant(rootView, skill, stamina, handicap,
						damageValue == null ? null : damageValue.getText()
								.toString());

			}

		});

		AlertDialog alert = builder.create();

		EditText skillValue = addCombatantView
				.findViewById(R.id.enemySkillValue);

		alert.setView(addCombatantView);

		mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
		skillValue.requestFocus();

		alert.show();
	}

	@Override
	protected void startCombat() {
		if (combatMode.equals(FF13_GUNFIGHT)) {
			for (int i = 0; i < combatPositions.size(); i++) {
				Combatant c = combatPositions.get(i);
				if (c != null)
					c.setDamage("1D6");
			}
		}
		super.startCombat();
	}

}
