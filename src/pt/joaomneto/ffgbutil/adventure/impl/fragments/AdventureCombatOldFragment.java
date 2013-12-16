package pt.joaomneto.ffgbutil.adventure.impl.fragments;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AdventureCombatOldFragment extends DialogFragment implements AdventureFragment {

	TextView enemySkillValue = null;
	TextView enemyStaminaValue = null;
	TextView handicapValue = null;

	Button attackButton = null;
	Button testCombatLuckButton = null;
	Button escapeButton = null;

	Button increaseStaminaButton = null;
	Button increaseSkillButton = null;
	Button increaseHandicapButton = null;

	Button decreaseStaminaButton = null;
	Button decreaseSkillButton = null;
	Button decreaseHandicapButton = null;

	int enemySkil = 0;
	int enemyStamina = 0;
	int handicap = 0;

	TextView combatResult = null;

	boolean hit = false;
	boolean draw = false;
	boolean round = false;

	public AdventureCombatOldFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_adventure_combat_old, container, false);

		final Adventure adv = (Adventure) getActivity();

		enemySkillValue = (TextView) rootView.findViewById(R.id.enemySkill);
		enemyStaminaValue = (TextView) rootView.findViewById(R.id.enemyStamina);
		handicapValue = (TextView) rootView.findViewById(R.id.handicap);

		combatResult = (TextView) rootView.findViewById(R.id.combatResult);

		increaseStaminaButton = (Button) rootView.findViewById(R.id.plusStaminaButton);
		increaseSkillButton = (Button) rootView.findViewById(R.id.plusSkillButton);
		increaseHandicapButton = (Button) rootView.findViewById(R.id.plusHandicapButton);

		decreaseStaminaButton = (Button) rootView.findViewById(R.id.minusStaminaButton);
		decreaseSkillButton = (Button) rootView.findViewById(R.id.minusSkillButton);
		decreaseHandicapButton = (Button) rootView.findViewById(R.id.minusHandicapButton);

		attackButton = (Button) rootView.findViewById(R.id.buttonAttack);
		testCombatLuckButton = (Button) rootView.findViewById(R.id.buttonTestCombatLuck);
		escapeButton = (Button) rootView.findViewById(R.id.buttonEscape);

		increaseStaminaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemyStamina++;
				updateValues();

			}
		});

		increaseSkillButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemySkil++;
				updateValues();

			}
		});

		increaseHandicapButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				handicap++;
				updateValues();

			}
		});

		decreaseStaminaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemyStamina--;
				updateValues();

			}
		});

		decreaseSkillButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemySkil--;
				updateValues();

			}
		});

		decreaseHandicapButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				handicap--;
				updateValues();

			}
		});

		attackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (enemyStamina == 0 || adv.getCurrentStamina() == 0)
					return;
				round = true;
				combatResult.setText("");

				int me = DiceRoller.roll2D6() + adv.getCurrentSkill() + handicap;
				Integer enemySkill = Integer.valueOf(enemySkillValue.getText().toString());
				int enemy = DiceRoller.roll2D6() + enemySkill;

				if (me > enemy) {
					draw = false;
					hit = true;
					enemyStamina -= 2;
					if (enemyStamina <= 0) {
						enemyStamina = 0;
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
						combatResult.setText("You've been hit! You lose 2 stamina points");
					}

				} else {
					draw = true;
					combatResult.setText("Draw...");
				}
				updateValues();
			}
		});

		testCombatLuckButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (draw || !round)
					return;
				round = false;
				boolean result = adv.testLuckInternal();
				if (result) {
					combatResult.setText("You're lucky!");
					if (hit) {
						enemyStamina--;
						if (enemyStamina <= 0) {
							enemyStamina = 0;
							adv.showAlert("You've defeated your opponent!");
						}
					} else {
						adv.setCurrentStamina(adv.getCurrentStamina() + 1);
					}
				} else {
					combatResult.setText("You're unlucky...");
					if (hit) {
						enemyStamina++;

					} else {
						adv.setCurrentStamina(adv.getCurrentStamina() - 1);
					}
					if (adv.getCurrentStamina() == 0) {
						adv.showAlert("You're dead...");
					}
				}
				updateValues();
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
					combatResult.setText("You've escaped! You lose 2 stamina points");
				}

			}
		});

		updateValues();

		return rootView;
	}

	public void updateValues() {
		enemySkillValue.setText("" + enemySkil);
		enemyStaminaValue.setText("" + enemyStamina);
		handicapValue.setText("" + handicap);
	}

	@Override
	public void refreshScreensFromResume() {

	}
}
