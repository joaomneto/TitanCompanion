package pt.joaomneto.ffgbutil.adventure.impl.fragments;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.impl.Adventure;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class AdventureCombatFragment extends DialogFragment {

	NumberPicker enemySkillValue = null;
	NumberPicker enemyStaminaValue = null;

	Button attackButton = null;
	Button testCombatLuckButton = null;
	Button escapeButton = null;

	TextView combatResult = null;

	boolean hit = false;
	boolean draw = false;
	boolean round = false;

	public AdventureCombatFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_adventure_combat,
				container, false);

		final Adventure adv = (Adventure) getActivity();

		enemySkillValue = (NumberPicker) rootView
				.findViewById(R.id.enemyskillPicker);
		enemyStaminaValue = (NumberPicker) rootView
				.findViewById(R.id.enemyStaminaPicker);

		combatResult = (TextView) rootView.findViewById(R.id.combatResult);

		enemySkillValue.setMinValue(0);
		enemySkillValue.setMaxValue(50);
		enemySkillValue.setValue(0);
		enemySkillValue.setWrapSelectorWheel(false);
		enemyStaminaValue.setMinValue(0);
		enemyStaminaValue.setMaxValue(50);
		enemyStaminaValue.setValue(0);
		enemyStaminaValue.setWrapSelectorWheel(false);

		attackButton = (Button) rootView.findViewById(R.id.buttonAttack);
		testCombatLuckButton = (Button) rootView
				.findViewById(R.id.buttonTestCombatLuck);
		escapeButton = (Button) rootView.findViewById(R.id.buttonEscape);

		attackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (enemyStaminaValue.getValue() == 0
						|| adv.getCurrentStamina() == 0)
					return;
				round = true;
				combatResult.setText("");
				int me = DiceRoller.roll2D6() + adv.getCurrentSkill();
				int enemy = DiceRoller.roll2D6() + enemySkillValue.getValue();

				if (me > enemy) {
					draw = false;
					hit = true;
					enemyStaminaValue.setValue(enemyStaminaValue.getValue() - 2);
					if (enemyStaminaValue.getValue() <= 0) {
						enemyStaminaValue.setValue(0);
						adv.showAlert("You've defeated your opponent!");
					} else {
						combatResult.setText("You hit your opponent!");
					}
				} else if (enemy > me) {
					draw = false;
					hit = false;
					adv.setCurrentStamina(adv.getCurrentStamina() - 2);
					if (adv.getCurrentStamina() < 0) {
						adv.setCurrentStamina(0);
						adv.showAlert("You're dead...");
					} else if (adv.getCurrentStamina() == 0) {
						adv.setCurrentStamina(0);
						adv.showAlert("You've been dealt a fatal blow (try your luck!)");
					} else {
						combatResult
								.setText("You've been hit! You lose 2 stamina points");
					}

				} else {
					draw = true;
					adv.showAlert("You've been dealt a fatal blow (try your luck!)");
				}
			}
		});

		testCombatLuckButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (draw || !round)
					return;
				round=false;
				boolean result = adv.testLuckInternal();
				if (result) {
					combatResult.setText("You're lucky!");
					if (hit) {
						enemyStaminaValue.setValue(enemyStaminaValue.getValue() - 1);
						if (enemyStaminaValue.getValue() <= 0) {
							enemyStaminaValue.setValue(0);
							adv.showAlert("You've defeated your opponent!");
						}
					} else {
						adv.setCurrentStamina(adv.getCurrentStamina() + 1);
					}
				} else {
					combatResult.setText("You're unlucky...");
					if (hit) {
						enemyStaminaValue.setValue(enemyStaminaValue.getValue() + 1);

					} else {
						adv.setCurrentStamina(adv.getCurrentStamina() - 1);
					}
					if (adv.getCurrentStamina() == 0) {
						adv.showAlert("You're dead...");
					}
				}
			}
		});

		escapeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				adv.setCurrentStamina(adv.getCurrentStamina() - 2);
				hit = false;
				draw = false;
				if (adv.getCurrentStamina() < 0) {
					adv.setCurrentStamina(0);
					adv.showAlert("You're dead...");
				} else if (adv.getCurrentStamina() == 0) {
					adv.setCurrentStamina(0);
					adv.showAlert("You've been dealt a fatal blow (try your luck!)");
				} else {
					combatResult
							.setText("You've escaped! You lose 2 stamina points");
				}

			}
		});

		return rootView;
	}

}
