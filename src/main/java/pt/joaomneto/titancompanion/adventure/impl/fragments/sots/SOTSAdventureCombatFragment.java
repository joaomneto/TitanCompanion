package pt.joaomneto.titancompanion.adventure.impl.fragments.sots;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.SOTSAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sots.SOTSMartialArt;

public class SOTSAdventureCombatFragment extends AdventureCombatFragment {

    protected boolean firstRound = true;
    protected Integer enemyDiceRoll = null;

    protected void sequenceCombatTurn() {

        Combatant position = getCurrentEnemy();

        SOTSAdventure adv = (SOTSAdventure) getActivity();

        if (adv.getSkill().equals(SOTSMartialArt.IAIJUTSU) && firstRound) {
            int damage = 3;
            position.setCurrentStamina(Math.max(0, position.getCurrentStamina() - damage));
            position.setStaminaLoss(position.getStaminaLoss() + damage);
            setHit(true);
            firstRound = false;
            getCombatResult().setText(R.string.iaijutsuFastDraw);
        } else {

            super.sequenceCombatTurn();
        }

    }

    protected void standardCombatTurn() {
        Combatant position = getCurrentEnemy();

        SOTSAdventure adv = (SOTSAdventure) getActivity();

        if (adv.getSkill().equals(SOTSMartialArt.IAIJUTSU) && firstRound) {
            int damage = 3;
            position.setCurrentStamina(Math.max(0, position.getCurrentStamina() - damage));
            position.setStaminaLoss(position.getStaminaLoss() + damage);
            setHit(true);
            firstRound = false;
            getCombatResult().setText(R.string.iaijutsuFastDraw);
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
    protected void resetCombat(boolean clearResults) {
        super.resetCombat(clearResults);
        firstRound = true;
        enemyDiceRoll = null;
    }

}
