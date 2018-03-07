package pt.joaomneto.titancompanion.adventure.impl.fragments.rp;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll;
import pt.joaomneto.titancompanion.util.DiceRoller;

public class RPAdventureCombatFragment extends AdventureCombatFragment {

    boolean unarmed = false;


    protected void addCombatButtonOnClick() {

        addCombatButtonOnClick(R.layout.component_18rp_add_combatant);

    }

    @Override
    protected void confirmCombatAction(InputMethodManager mgr, View addCombatantView) {

        CheckBox unarmedValue = addCombatantView.findViewById(R.id.unarmedValue);
        unarmed = unarmedValue.isChecked();

        super.confirmCombatAction(mgr, addCombatantView);
    }

    @Override
    protected Boolean suddenDeath(DiceRoll diceRoll, DiceRoll enemyDiceRoll) {
        if (unarmed) {
            return DiceRoller.rollD6() == 6;
        }
        return super.suddenDeath(diceRoll, enemyDiceRoll);
    }
}
