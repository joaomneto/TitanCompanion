package pt.joaomneto.ffgbutil.adventure.impl.fragments.ss;

import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.impl.SSAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureSpellsFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SSAdventureSpellsFragment extends AdventureSpellsFragment {

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		adv = (SSAdventure) getActivity();
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	protected void specificSpellActivation(
			final Adventure adv, String spell) {
		if (spell.equals("Skill")) {
			adv.setCurrentSkill(adv.getCurrentSkill()
					+ (adv.getInitialSkill() / 2));
			if (adv.getCurrentSkill() > adv
					.getInitialSkill())
				adv.setCurrentSkill(adv
						.getInitialSkill());
		} else if (spell.equals("Stamina")) {
			adv.setCurrentStamina(adv
					.getCurrentStamina()
					+ (adv.getInitialStamina() / 2));
			if (adv.getCurrentStamina() > adv
					.getInitialStamina())
				adv.setCurrentStamina(adv
						.getInitialStamina());
		} else if (spell.equals("Luck")) {
			adv.setCurrentLuck(adv.getCurrentLuck()
					+ (adv.getInitialLuck() / 2));
			if (adv.getCurrentLuck() > adv
					.getInitialLuck())
				adv.setCurrentLuck(adv.getInitialLuck());
		}
	}

	protected String[] getSpellList() {
		return new String[] { "Skill", "Stamina",
				"Luck", "Fire", "Ice", "Illusion", "Friendship", "Growth",
				"Bless", "Fear", "Withering", "Curse" };
	}

}
