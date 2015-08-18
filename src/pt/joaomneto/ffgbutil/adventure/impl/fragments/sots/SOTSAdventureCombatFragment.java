package pt.joaomneto.ffgbutil.adventure.impl.fragments.sots;

import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.impl.SOTSAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.widget.LinearLayout;

public class SOTSAdventureCombatFragment extends AdventureCombatFragment {

	protected boolean firstRound;

	protected void sequenceCombatTurn() {

		Combatant position = combatPositions.get(currentCombat);

		if (!finishedCombats.contains(currentCombat)) {
			draw = false;
			luckTest = false;
			hit = false;
			LinearLayout row = (LinearLayout) rootView
					.findViewById(gridRows[currentCombat]);
			SOTSAdventure adv = (SOTSAdventure) getActivity();

			if (adv.getSkill().equals(SOTSAdventure.SKILL_IAIJUTSU) && firstRound) {
				int damage = 3;
				position.setCurrentStamina(Math.max(0,
						position.getCurrentStamina() - damage));
				position.setStaminaLoss(position.getStaminaLoss() + damage);
				hit = true;
				firstRound = false;
				combatResult
						.setText("You have hit the enemy with the Iaijutsu fast draw strike (-3 ST)");
			} else {

				int diceRoll = DiceRoller.roll2D6();
				int skill = adv.getCombatSkillValue();
				int attackStrength = diceRoll + skill + position.getHandicap();
				int enemyDiceRoll = DiceRoller.roll2D6();
				int enemyAttackStrength = enemyDiceRoll
						+ position.getCurrentSkill();
				if (attackStrength > enemyAttackStrength) {
					if (!position.isDefenseOnly()) {
						Boolean suddenDeath = suddenDeath();
						if (suddenDeath == null) {
							int damage = getDamage();
							position.setCurrentStamina(Math.max(0,
									position.getCurrentStamina() - damage));
							hit = true;
							combatResult
									.setText("You have hit the enemy! ("
											+ diceRoll
											+ " + "
											+ skill
											+ (position.getHandicap() >= 0 ? (" + " + position
													.getHandicap()) : "")
											+ ") vs (" + enemyDiceRoll + " + "
											+ position.getCurrentSkill()
											+ "). (-" + damage + "ST)");
						} else {
							position.setCurrentStamina(0);
							adv.showAlert("You've defeated an enemy by sudden death!");
						}
					} else {
						draw = true;
						combatResult
								.setText("You have blocked the enemy attack! ("
										+ diceRoll
										+ " + "
										+ skill
										+ (position.getHandicap() >= 0 ? (" + " + position
												.getHandicap()) : "")
										+ ") vs (" + enemyDiceRoll + " + "
										+ position.getCurrentSkill() + ")");
					}
				} else if (attackStrength < enemyAttackStrength) {
					int damage = convertDamageStringToInteger(position
							.getDamage());
					adv.setCurrentStamina((Math.max(0, adv.getCurrentStamina()
							- damage)));
					combatResult.setText("You've been hit... ("
							+ diceRoll
							+ " + "
							+ skill
							+ (position.getHandicap() >= 0 ? (" + " + position
									.getHandicap()) : "") + ") vs ("
							+ enemyDiceRoll + " + "
							+ position.getCurrentSkill() + "). (-" + damage
							+ "ST)");
				} else {

					combatResult.setText("Both you and the enemy have missed");
					draw = true;
				}
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

	protected void standardCombatTurn() {
		Combatant position = combatPositions.get(currentCombat);

		if (!finishedCombats.contains(currentCombat)) {
			draw = false;
			luckTest = false;
			hit = false;
			SOTSAdventure adv = (SOTSAdventure) getActivity();
			LinearLayout row = (LinearLayout) rootView
					.findViewById(gridRows[currentCombat]);

			if (adv.getSkill().equals(SOTSAdventure.SKILL_IAIJUTSU) && firstRound) {
				int damage = 3;
				position.setCurrentStamina(Math.max(0,
						position.getCurrentStamina() - damage));
				position.setStaminaLoss(position.getStaminaLoss() + damage);
				hit = true;
				firstRound = false;
				combatResult
						.setText("You have hit the enemy with the Iaijutsu fast draw strike (-3 ST)");
			} else {

				int diceRoll = DiceRoller.roll2D6();
				int skill = adv.getCombatSkillValue();
				int attackStrength = diceRoll + skill + position.getHandicap();
				int enemyDiceRoll = DiceRoller.roll2D6();
				int enemyAttackStrength = enemyDiceRoll
						+ position.getCurrentSkill();
				if (attackStrength > enemyAttackStrength) {
					Boolean suddenDeath = suddenDeath();
					if (suddenDeath == null) {
						int damage = getDamage();

						position.setCurrentStamina(Math.max(0,
								position.getCurrentStamina() - getDamage()));
						position.setStaminaLoss(position.getStaminaLoss()
								+ damage);
						hit = true;
						combatResult
								.setText("You have hit the enemy! ("
										+ diceRoll
										+ " + "
										+ skill
										+ (position.getHandicap() >= 0 ? (" + " + position
												.getHandicap()) : "")
										+ ") vs (" + enemyDiceRoll + " + "
										+ position.getCurrentSkill() + ")");
					} else {
						position.setCurrentStamina(0);
						adv.showAlert("You've defeated an enemy by sudden death!");
					}

				} else if (attackStrength < enemyAttackStrength) {
					int damage = convertDamageStringToInteger(position
							.getDamage());
					staminaLoss += damage;
					adv.setCurrentStamina((Math.max(0, adv.getCurrentStamina()
							- damage)));
					combatResult.setText("Youve have been hit... ("
							+ diceRoll
							+ " + "
							+ skill
							+ (position.getHandicap() >= 0 ? (" + " + position
									.getHandicap()) : "") + ") vs ("
							+ enemyDiceRoll + " + "
							+ position.getCurrentSkill() + ")");
				} else {

					combatResult.setText("Both you and the enemy have missed");
					draw = true;
				}
			}

			if (position.getCurrentStamina() == 0
					|| (getKnockoutStamina() != null && position
							.getStaminaLoss() >= getKnockoutStamina())) {
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

	@Override
	protected void advanceCombat() {
		super.advanceCombat();
		firstRound = true;
	}

	@Override
	protected void resetCombat() {
		super.resetCombat();
		firstRound = true;
	}

}
