package pt.joaomneto.ffgbutil.adventure.impl.fragments.tot;

import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.impl.TOTAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureSpellsFragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TOTAdventureSpellsFragment extends AdventureSpellsFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		adv = (TOTAdventure) getActivity();
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	protected void specificSpellActivation(final Adventure adv, String spell) {
		if (spell.equals("Open Door")) {
			adv.changeStamina(-2);

		} else if (spell.equals("Creature Sleep")) {
			adv.changeStamina(-1);

		} else if (spell.equals("Magic Arrow")) {
			adv.changeStamina(-2);

		} else if (spell.equals("Language")) {
			adv.changeStamina(-1);

		} else if (spell.equals("Read Symbols")) {
			adv.changeStamina(-1);

		} else if (spell.equals("Light")) {
			adv.changeStamina(-2);

		} else if (spell.equals("Fire")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(adv);
			builder.setTitle("Choose Stamina cost?")
					.setCancelable(false)
					.setNegativeButton("2",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									adv.changeStamina(-2);
									dialog.cancel();
								}
							});
			builder.setPositiveButton("1",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							adv.changeStamina(-1);
							dialog.cancel();
						}

					});

			AlertDialog alert = builder.create();
			alert.show();

		} else if (spell.equals("Jump")) {
			adv.changeStamina(-3);

		} else if (spell.equals("Detect Trap")) {
			adv.changeStamina(-2);

		} else if (spell.equals("Create Water")) {
		}
	}

	protected String[] getSpellList() {
		return new String[] { "Open Door", "Creature Sleep", "Magic Arrow",
				"Language", "Read Symbols", "Light", "Fire", "Jump",
				"Detect Trap", "Create Water" };
	}

	protected boolean getSingleUse() {
		return false;
	}

}
