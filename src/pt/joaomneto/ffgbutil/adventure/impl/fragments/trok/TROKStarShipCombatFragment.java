package pt.joaomneto.ffgbutil.adventure.impl.fragments.trok;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.TROKAdventure;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TROKStarShipCombatFragment extends AdventureFragment {

	TextView starshipWeaponsValue = null;
	TextView starshipShieldsValue = null;
	TextView starshipMissilesValue = null;
	TextView enemyWeaponsValue = null;
	TextView enemyShieldsValue = null;
	TextView enemy2WeaponsValue = null;
	TextView enemy2ShieldsValue = null;

	Button attackButton = null;

	int enemyWeapons = 0;
	int enemyShields = 0;
	int enemy2Weapons = 0;
	int enemy2Shields = 0;

	TextView combatResult = null;

	public TROKStarShipCombatFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_15trok_adventure_starshipcombat, container, false);

		final TROKAdventure adv = (TROKAdventure) getActivity();

		starshipWeaponsValue = (TextView) rootView.findViewById(R.id.starshipWeaponsValue);
		starshipShieldsValue = (TextView) rootView.findViewById(R.id.starshipShieldsValue);
		starshipMissilesValue = (TextView) rootView.findViewById(R.id.starshipMissilesValue);
		
		enemyWeaponsValue = (TextView) rootView.findViewById(R.id.enemyWeaponsValue);
		enemyShieldsValue = (TextView) rootView.findViewById(R.id.enemyShieldsValue);
		enemy2WeaponsValue = (TextView) rootView.findViewById(R.id.enemy2WeaponsValue);
		enemy2ShieldsValue = (TextView) rootView.findViewById(R.id.enemy2ShieldsValue);

		starshipWeaponsValue.setText("" + adv.getCurrentWeapons());
		starshipShieldsValue.setText("" + adv.getCurrentShields());

		combatResult = (TextView) rootView.findViewById(R.id.combatResult);

		setupIncDecButton(rootView, R.id.plusWeaponsButton, R.id.minusWeaponsButton, adv, TROKAdventure.class, "getCurrentWeapons", "setCurrentWeapons", adv.getInitialWeapons());
		setupIncDecButton(rootView, R.id.plusShieldsButton, R.id.minusShieldsButton, adv, TROKAdventure.class, "getCurrentShields", "setCurrentShields", adv.getInitialShields());
		setupIncDecButton(rootView, R.id.plusMissilesButton, R.id.minusMissilesButton, adv, TROKAdventure.class, "getMissiles", "setMissiles", 99);

		setupIncDecButton(rootView, R.id.plusEnemyWeaponsButton, R.id.minusEnemyWeaponsButton, "getEnemyWeapons", "setEnemyWeapons", 99);
		setupIncDecButton(rootView, R.id.plusEnemyShieldsButton, R.id.minusEnemyShieldsButton, "getEnemyShields", "setEnemyShields", 99);

		setupIncDecButton(rootView, R.id.plusEnemy2WeaponsButton, R.id.minusEnemy2WeaponsButton, "getEnemy2Weapons", "setEnemy2Weapons", 99);
		setupIncDecButton(rootView, R.id.plusEnemy2ShieldsButton, R.id.minusEnemy2ShieldsButton, "getEnemy2Shields", "setEnemy2Shields", 99);

		attackButton = (Button) rootView.findViewById(R.id.buttonAttack);

		attackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (enemyShields == 0 || adv.getCurrentShields() == 0)
					return;

				combatResult.setText("");

				if (enemyShields > 0) {
					if (DiceRoller.roll2D6() <= adv.getCurrentWeapons()) {
						int damage = 1;
						enemyShields -= damage;
						if (enemyShields <= 0) {
							enemyShields = 0;
							adv.showAlert("Direct hit!. You've defeated your opponent!");
						} else {
							combatResult.setText("Direct hit! (-" + damage + " Shields)");
						}
					} else {
						combatResult.setText("You've missed!");
					}
				} else if (enemy2Shields > 0) {
					if (DiceRoller.roll2D6() <= adv.getCurrentWeapons()) {
						int damage = 1;
						enemy2Shields -= damage;
						if (enemy2Shields <= 0) {
							enemy2Shields = 0;
							adv.showAlert("Direct hit!. You've defeated the second opponent!");
						} else {
							combatResult.setText("Direct hit! (-" + damage + " Shields)");
						}
					} else {
						combatResult.setText("You've missed!");
					}
				} else {
					return;
				}
				if (enemyShields > 0) {
					if (DiceRoller.roll2D6() <= enemyWeapons) {
						int damage = 1;
						adv.setCurrentShields(adv.getCurrentShields() - damage);
						if (adv.getCurrentShields() <= 0) {
							adv.setCurrentShields(0);
							adv.showAlert("The enemy has destroyed your starship...");
						} else {
							combatResult.setText(combatResult.getText() + "\nThe enemy has hit your starship. (-" + damage + " Shields)");
						}
					} else {
						combatResult.setText(combatResult.getText() + "\nYou're enemy has missed!");
					}
				}
				

				if (enemy2Shields > 0) {
					if (DiceRoller.roll2D6() <= enemy2Weapons) {
						int damage = 1;
						adv.setCurrentShields(adv.getCurrentShields() - damage);
						if (adv.getCurrentShields() <= 0) {
							adv.setCurrentShields(0);
							adv.showAlert("The enemy has destroyed your starship...");
						} else {
							combatResult.setText(combatResult.getText() + "\nThe second enemy has hit your starship. (-" + damage + " Shields)");
						}
					} else {
						combatResult.setText(combatResult.getText() + "\nThe second enemy has missed!");
					}
				}

				refreshScreensFromResume();
			}
		});

		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {

		TROKAdventure adv = (TROKAdventure) getActivity();

		enemyShieldsValue.setText("" + enemyShields);
		enemyWeaponsValue.setText("" + enemyWeapons);
		enemy2ShieldsValue.setText("" + enemy2Shields);
		enemy2WeaponsValue.setText("" + enemy2Weapons);
		starshipShieldsValue.setText("" + adv.getCurrentShields());
		starshipWeaponsValue.setText("" + adv.getCurrentWeapons());
		starshipMissilesValue.setText("" + adv.getMissiles());
	}

	public int getEnemyWeapons() {
		return enemyWeapons;
	}

	public void setEnemyWeapons(int enemyWeapons) {
		this.enemyWeapons = enemyWeapons;
	}

	public int getEnemyShields() {
		return enemyShields;
	}

	public void setEnemyShields(int enemyShields) {
		this.enemyShields = enemyShields;
	}

	public int getEnemy2Weapons() {
		return enemy2Weapons;
	}

	public void setEnemy2Weapons(int enemy2Weapons) {
		this.enemy2Weapons = enemy2Weapons;
	}

	public int getEnemy2Shields() {
		return enemy2Shields;
	}

	public void setEnemy2Shields(int enemy2Shields) {
		this.enemy2Shields = enemy2Shields;
	}

}
