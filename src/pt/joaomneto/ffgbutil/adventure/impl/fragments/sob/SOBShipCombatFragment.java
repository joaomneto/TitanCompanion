package pt.joaomneto.ffgbutil.adventure.impl.fragments.sob;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.SOBAdventure;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SOBShipCombatFragment extends AdventureFragment {

	TextView starshipCrewStrikeValue = null;
	TextView starshipCrewStrengthValue = null;
	TextView enemyCrewStrikeValue = null;
	TextView enemyCrewStrengthValue = null;

	Button attackButton = null;

	int enemyCrewStrike = 0;
	int enemyCrewStrength = 0;

	TextView combatResult = null;

	public SOBShipCombatFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_16sob_adventure_shipcombat, container, false);

		final SOBAdventure adv = (SOBAdventure) getActivity();

		starshipCrewStrikeValue = (TextView) rootView.findViewById(R.id.starshipCrewStrikeValue);
		starshipCrewStrengthValue = (TextView) rootView.findViewById(R.id.starshipCrewStrengthValue);

		enemyCrewStrikeValue = (TextView) rootView.findViewById(R.id.enemyCrewStrikeValue);
		enemyCrewStrengthValue = (TextView) rootView.findViewById(R.id.enemyCrewStrengthValue);

		starshipCrewStrikeValue.setText("" + adv.getCurrentCrewStrike());
		starshipCrewStrengthValue.setText("" + adv.getCurrentCrewStrength());

		combatResult = (TextView) rootView.findViewById(R.id.combatResult);

		setupIncDecButton(rootView, R.id.plusCrewStrikeButton, R.id.minusCrewStrikeButton, adv, SOBAdventure.class, "getCurrentCrewStrike", "setCurrentCrewStrike", adv.getInitialCrewStrike());
		setupIncDecButton(rootView, R.id.plusCrewStrengthButton, R.id.minusCrewStrengthButton, adv, SOBAdventure.class, "getCurrentCrewStrength", "setCurrentCrewStrength", adv.getInitialCrewStrength());

		setupIncDecButton(rootView, R.id.plusEnemyCrewStrikeButton, R.id.minusEnemyCrewStrikeButton, "getEnemyCrewStrike", "setEnemyCrewStrike", 99);
		setupIncDecButton(rootView, R.id.plusEnemyCrewStrengthButton, R.id.minusEnemyCrewStrengthButton, "getEnemyCrewStrength", "setEnemyCrewStrength", 99);


		attackButton = (Button) rootView.findViewById(R.id.buttonAttack);

		attackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (enemyCrewStrength == 0 || adv.getCurrentCrewStrength() == 0)
					return;

				combatResult.setText("");

				if (enemyCrewStrength > 0) {
					int attackStrength = DiceRoller.roll2D6()+ adv.getCurrentCrewStrike();
					int énemyStrength = DiceRoller.roll2D6()+ enemyCrewStrike;
					
					if (attackStrength>énemyStrength) {
						int damage = 2;
						enemyCrewStrength -= damage;
						if (enemyCrewStrength <= 0) {
							enemyCrewStrength = 0;
							adv.showAlert("Direct hit!. You've defeated your opponent!");
						} else {
							combatResult.setText("Direct hit! (-" + damage + " CrewStrength)");
						}
					} else if (attackStrength<énemyStrength) {
						int damage = 2;
						adv.setCurrentCrewStrength(adv.getCurrentCrewStrength() - damage);
						if (adv.getCurrentCrewStrength() <= 0) {
							adv.setCurrentCrewStrength(0);
							adv.showAlert("The enemy has destroyed your ship...");
						} else {
							combatResult.setText(combatResult.getText() + "\nThe enemy has hit your ship. (-" + damage + " CrewStrength)");
						}
					}else {
						combatResult.setText("Both ships missed!");
					}
				} else {
					return;
				}
				
				refreshScreensFromResume();
			}
		});

		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {

		SOBAdventure adv = (SOBAdventure) getActivity();

		enemyCrewStrengthValue.setText("" + enemyCrewStrength);
		enemyCrewStrikeValue.setText("" + enemyCrewStrike);
		starshipCrewStrengthValue.setText("" + adv.getCurrentCrewStrength());
		starshipCrewStrikeValue.setText("" + adv.getCurrentCrewStrike());
	}

	public int getEnemyCrewStrike() {
		return enemyCrewStrike;
	}

	public void setEnemyCrewStrike(int enemyCrewStrike) {
		this.enemyCrewStrike = enemyCrewStrike;
	}

	public int getEnemyCrewStrength() {
		return enemyCrewStrength;
	}

	public void setEnemyCrewStrength(int enemyCrewStrength) {
		this.enemyCrewStrength = enemyCrewStrength;
	}


}
