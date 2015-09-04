package pt.joaomneto.ffgbutil.adventure.impl.fragments.coh;

import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.ffgbutil.adventure.impl.util.DiceRoll;

public class COHAdventureCombatFragment extends AdventureCombatFragment {

	protected Boolean suddenDeath(DiceRoll diceRoll, DiceRoll enemyDiceRoll) {
		return diceRoll.getD1()==diceRoll.getD2();
	}
	
	protected String getDefaultEnemyDamage() {
		return "1";
	}

}
