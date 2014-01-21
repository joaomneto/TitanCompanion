package pt.joaomneto.ffgbutil.adventure.impl.fragments.rp;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.view.View;
import android.widget.CheckBox;

public class RPAdventureCombatFragment extends AdventureCombatFragment {

	boolean unarmed = false;

	protected void addCombatButtonOnClick() {
		Adventure adv = (Adventure) getActivity();
		final View addCombatantView = adv.getLayoutInflater().inflate(R.layout.component_add_combatant, null);

		CheckBox unarmedValue = (CheckBox) addCombatantView.findViewById(R.id.unarmedValue);
		unarmed = unarmedValue.isChecked();

		addCombatButtonOnClick(R.layout.component_18rp_add_combatant);
	}
	
	protected Boolean suddenDeath(){
		return unarmed?DiceRoller.rollD6()==6:null;
	}

}
