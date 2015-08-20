package pt.joaomneto.ffgbutil.adventure.impl.fragments.ff;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.FFAdventure;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FFVehicleCombatFragment extends AdventureFragment {

	TextView vehicleFirepowerValue = null;
	TextView vehicleArmourValue = null;
	TextView enemyFirepowerValue = null;
	TextView enemyArmourValue = null;
	TextView enemy2FirepowerValue = null;
	TextView enemy2ArmourValue = null;

	Button attackButton = null;

	int enemyFirepower = 0;
	int enemyArmour = 0;

	int enemy2Firepower = 0;
	int enemy2Armour = 0;
	TextView combatResult = null;

	public FFVehicleCombatFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_13ff_adventure_vehiclecombat, container,
				false);

		final FFAdventure adv = (FFAdventure) getActivity();

		vehicleFirepowerValue = (TextView) rootView
				.findViewById(R.id.vehiclefirepowerValue);
		vehicleArmourValue = (TextView) rootView
				.findViewById(R.id.vehiclearmorValue);
		enemyFirepowerValue = (TextView) rootView
				.findViewById(R.id.enemyfirepowerValue);
		enemyArmourValue = (TextView) rootView
				.findViewById(R.id.enemyarmorValue);
		enemy2FirepowerValue = (TextView) rootView
				.findViewById(R.id.enemy2firepowerValue);
		enemy2ArmourValue = (TextView) rootView
				.findViewById(R.id.enemy2armorValue);

		vehicleFirepowerValue.setText("" + adv.getCurrentFirepower());
		vehicleArmourValue.setText("" + adv.getCurrentArmour());

		combatResult = (TextView) rootView.findViewById(R.id.combatResult);

		setupIncDecButton(rootView, R.id.plusfirepowerButton,
				R.id.minusfirepowerButton, adv, FFAdventure.class,
				"getCurrentFirepower", "setCurrentFirepower",
				adv.getInitialFirepower());
		setupIncDecButton(rootView, R.id.plusarmorButton,
				R.id.minusarmorButton, adv, FFAdventure.class,
				"getCurrentArmour", "setCurrentArmour", adv.getInitialArmour());

		setupIncDecButton(rootView, R.id.plusEnemyfirepowerButton,
				R.id.minusEnemyfirepowerButton, "getEnemyFirepower",
				"setEnemyFirepower", 100);
		setupIncDecButton(rootView, R.id.plusEnemyarmorButton,
				R.id.minusEnemyarmorButton, "getEnemyArmour", "setEnemyArmour",
				100);

		setupIncDecButton(rootView, R.id.plusEnemy2firepowerButton,
				R.id.minusEnemy2firepowerButton, "getEnemy2Firepower",
				"setEnemy2Firepower", 100);
		setupIncDecButton(rootView, R.id.plusEnemy2armorButton,
				R.id.minusEnemy2armorButton, "getEnemy2Armour",
				"setEnemy2Armour", 100);

		attackButton = (Button) rootView.findViewById(R.id.buttonAttack);

		attackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ((enemyArmour == 0 && enemy2Armour == 0 )|| adv.getCurrentArmour() == 0)
					return;

				combatResult.setText("");

				int myAttack = DiceRoller.roll2D6() + adv.getCurrentFirepower();
				int enemyAttack = DiceRoller.roll2D6() + enemyFirepower;

				if (myAttack > enemyAttack) {
					int damage = DiceRoller.rollD6();
					enemyArmour -= damage;
					if (enemyArmour <= 0) {
						enemyArmour = 0;
						Adventure.showAlert("Direct hit!. You've defeated your opponent!",adv);
					} else {
						combatResult.setText("Direct hit! (-" + damage
								+ " Armour)");
					}
				} else if (enemyAttack > enemyFirepower) {
					int damage = DiceRoller.rollD6();
					adv.setCurrentArmour(adv.getCurrentArmour() - damage);
					if (adv.getCurrentArmour() <= 0) {
						adv.setCurrentArmour(0);
						Adventure.showAlert("The enemy has destroyed your vehicle...",adv);
					} else {
						combatResult
								.setText("The enemy has hit your vehicle. (-"
										+ damage + " Armour)");
					}
				} else {
					combatResult
							.setText("Both you and your enemy have missed!");
				}

				if (enemy2Armour > 0 && enemy2Firepower > 0) {
					myAttack = DiceRoller.roll2D6() + adv.getCurrentFirepower();
					enemyAttack = DiceRoller.roll2D6() + enemy2Firepower;
					if (myAttack > enemyAttack) {
						if (enemyArmour > 0) {
							combatResult.setText(combatResult.getText()
									+ "\nYou've avoided the second enemy attack.");
						} else {
							int damage = DiceRoller.rollD6();
							enemy2Armour -= damage;
							if (enemy2Armour <= 0) {
								enemy2Armour = 0;
								Adventure.showAlert("Direct hit!. You've defeated your opponent!",adv);
							} else {
								combatResult.setText("Direct hit! (-" + damage
										+ " Armour)");
							}
						}

					} else if (enemyAttack > enemyFirepower) {
						int damage = DiceRoller.rollD6();
						adv.setCurrentArmour(adv.getCurrentArmour() - damage);
						if (adv.getCurrentArmour() <= 0) {
							adv.setCurrentArmour(0);
							Adventure.showAlert("The enemy has destroyed your vehicle...",adv);
						} else {
							combatResult.setText(combatResult.getText()
									+ "\nThe second enemy has hit your vehicle. (-"
									+ damage + " Armour)");
						}
					} else {
						if (enemyArmour > 0) {
							combatResult.setText(combatResult.getText()
									+ "\nYou've avoided the second enemy attack.");
						} else {
							combatResult.setText(combatResult.getText()
									+ "\nBoth you and your enemy have missed!");
						}
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

		FFAdventure adv = (FFAdventure) getActivity();

		enemyArmourValue.setText("" + enemyArmour);
		enemyFirepowerValue.setText("" + enemyFirepower);
		enemy2ArmourValue.setText("" + enemy2Armour);
		enemy2FirepowerValue.setText("" + enemy2Firepower);
		vehicleArmourValue.setText("" + adv.getCurrentArmour());
		vehicleFirepowerValue.setText("" + adv.getCurrentFirepower());
	}

	public int getEnemy2Firepower() {
		return enemy2Firepower;
	}

	public void setEnemy2Firepower(int enemy2Firepower) {
		this.enemy2Firepower = enemy2Firepower;
	}

	public int getEnemy2Armour() {
		return enemy2Armour;
	}

	public void setEnemy2Armour(int enemy2Armour) {
		this.enemy2Armour = enemy2Armour;
	}

	public int getEnemyFirepower() {
		return enemyFirepower;
	}

	public void setEnemyFirepower(int enemyFirepower) {
		this.enemyFirepower = enemyFirepower;
	}

	public int getEnemyArmour() {
		return enemyArmour;
	}

	public void setEnemyArmour(int enemyArmour) {
		this.enemyArmour = enemyArmour;
	}

}
