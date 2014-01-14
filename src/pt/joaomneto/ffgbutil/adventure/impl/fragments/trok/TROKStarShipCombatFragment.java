package pt.joaomneto.ffgbutil.adventure.impl.fragments.trok;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.FFAdventure;
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

	TextView vehicleWeaponsValue = null;
	TextView vehicleShieldsValue = null;
	TextView enemyWeaponsValue = null;
	TextView enemyShieldsValue = null;

	Button attackButton = null;

	int enemyWeapons = 0;
	int enemyShields = 0;

	TextView combatResult = null;

	public TROKStarShipCombatFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_15trok_adventure_starshipcombat, container, false);

		final TROKAdventure adv = (TROKAdventure) getActivity();

		vehicleWeaponsValue = (TextView) rootView.findViewById(R.id.vehicleWeaponsValue);
		vehicleShieldsValue = (TextView) rootView.findViewById(R.id.vehicleShieldsValue);
		enemyWeaponsValue = (TextView) rootView.findViewById(R.id.enemyWeaponsValue);
		enemyShieldsValue = (TextView) rootView.findViewById(R.id.enemyShieldsValue);

		vehicleWeaponsValue.setText("" + adv.getCurrentWeapons());
		vehicleShieldsValue.setText("" + adv.getCurrentShields());

		combatResult = (TextView) rootView.findViewById(R.id.combatResult);

		setupIncDecButton(rootView, R.id.plusWeaponsButton, R.id.minusWeaponsButton, adv, FFAdventure.class, "getCurrentWeapons", "setCurrentWeapons", adv.getInitialWeapons());
		setupIncDecButton(rootView, R.id.plusShieldsButton, R.id.minusShieldsButton, adv, FFAdventure.class, "getCurrentShields", "setCurrentShields", adv.getInitialShields());

		setupIncDecButton(rootView, R.id.plusEnemyWeaponsButton, R.id.minusEnemyWeaponsButton, "getEnemyWeapons", "setEnemyWeapons", 100);
		setupIncDecButton(rootView, R.id.plusEnemyShieldsButton, R.id.minusEnemyShieldsButton, "getEnemyShields", "setEnemyShields", 100);

		attackButton = (Button) rootView.findViewById(R.id.buttonAttack);

		attackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (enemyShields == 0 || adv.getCurrentShields() == 0)
					return;

				combatResult.setText("");

				int myAttack = DiceRoller.roll2D6() + adv.getCurrentWeapons();
				int enemyAttack = DiceRoller.roll2D6() + enemyWeapons;

				if (myAttack > enemyAttack) {
					int damage = DiceRoller.rollD6();
					enemyShields -= damage;
					if (enemyShields <= 0) {
						enemyShields = 0;
						adv.showAlert("Direct hit!. You've defeated your opponent!");
					} else {
						combatResult.setText("Direct hit! (-" + damage + " Shields)");
					}
				} else if (enemyAttack > enemyWeapons) {
					int damage = DiceRoller.rollD6();
					adv.setCurrentShields(adv.getCurrentShields() - damage);
					if (adv.getCurrentShields() <= 0) {
						adv.setCurrentShields(0);
						adv.showAlert("The enemy has destroyed your vehicle...");
					} else {
						combatResult.setText("The enemy has hit your vehicle. (-" + damage + " Shields)");
					}
				} else {
					combatResult.setText("Both you and your enemy have missed!");
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
		vehicleShieldsValue.setText("" + adv.getCurrentShields());
		vehicleWeaponsValue.setText("" + adv.getCurrentWeapons());
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

}
