package pt.joaomneto.ffgbutil.adventure.impl.fragments.sots;

import pt.joaomneto.ffgbutil.adventure.impl.SOTSAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment;

public class SOTSAdventureCombatFragment extends AdventureCombatFragment {

	protected boolean firstRound;
	protected Integer enemyDiceRoll = null;

	protected void sequenceCombatTurn() {

		Combatant position = getCurrentEnemy();

		SOTSAdventure adv = (SOTSAdventure) getActivity();

		if (adv.getSkill().equals(SOTSAdventure.SKILL_IAIJUTSU) && firstRound) {
			int damage = 3;
			position.setCurrentStamina(Math.max(0, position.getCurrentStamina() - damage));
			position.setStaminaLoss(position.getStaminaLoss() + damage);
			hit = true;
			firstRound = false;
			combatResult.setText("You have hit the enemy with the Iaijutsu fast draw strike (-3 ST)");
		} else {

			super.sequenceCombatTurn();
		}

	}

	protected void standardCombatTurn() {
		Combatant position = getCurrentEnemy();

		SOTSAdventure adv = (SOTSAdventure) getActivity();

		if (adv.getSkill().equals(SOTSAdventure.SKILL_IAIJUTSU) && firstRound) {
			int damage = 3;
			position.setCurrentStamina(Math.max(0, position.getCurrentStamina() - damage));
			position.setStaminaLoss(position.getStaminaLoss() + damage);
			hit = true;
			firstRound = false;
			combatResult.setText("You have hit the enemy with the Iaijutsu fast draw strike (-3 ST)");
		} else {

			super.standardCombatTurn();
		}

	}

	@Override
	protected void removeAndAdvanceCombat(Combatant combatant) {
		super.removeAndAdvanceCombat(combatant);
		firstRound = true;
	}

	@Override
	protected void resetCombat() {
		super.resetCombat();
		firstRound = true;
		enemyDiceRoll = null;
	}

}
