package pt.joaomneto.ffgbutil.adventure.impl.fragments.st;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.STAdventure;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class STStarshipCombatFragment extends DialogFragment implements AdventureFragment {

	TextView shipWeaponsValue = null;
	TextView shipShieldsValue = null;
	TextView enemyWeaponsValue = null;
	TextView enemyShieldsValue = null;

	Button attackButton = null;

	Button minusWeaponsButton = null;
	Button minusShieldsButton = null;
	Button plusWeaponsButton = null;
	Button plusShieldsButton = null;

	Button minusEnemyWeaponsButton = null;
	Button minusEnemyShieldsButton = null;
	Button plusEnemyWeaponsButton = null;
	Button plusEnemyShieldsButton = null;

	int enemyWeapons = 0;
	int enemyShields = 0;
	TextView combatResult = null;

	public STStarshipCombatFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_04st_adventure_starshipcombat, container, false);

		final STAdventure adv = (STAdventure) getActivity();

		shipWeaponsValue = (TextView) rootView.findViewById(R.id.shipWeaponsValue);
		shipShieldsValue = (TextView) rootView.findViewById(R.id.shipShieldsValue);
		enemyWeaponsValue = (TextView) rootView.findViewById(R.id.enemyWeaponsValue);
		enemyShieldsValue = (TextView) rootView.findViewById(R.id.enemyShieldsValue);

		shipWeaponsValue.setText(""+adv.getCurrentShipWeapons());
		shipShieldsValue.setText(""+adv.getCurrentShipShields());

		combatResult = (TextView) rootView.findViewById(R.id.combatResult);

		minusWeaponsButton = (Button) rootView.findViewById(R.id.minusWeaponsButton);
		minusShieldsButton = (Button) rootView.findViewById(R.id.minusShieldsButton);
		plusWeaponsButton = (Button) rootView.findViewById(R.id.plusWeaponsButton);
		plusShieldsButton = (Button) rootView.findViewById(R.id.plusShieldsButton);

		minusEnemyWeaponsButton = (Button) rootView.findViewById(R.id.minusEnemyWeaponsButton);
		minusEnemyShieldsButton = (Button) rootView.findViewById(R.id.minusEnemyShieldsButton);
		plusEnemyWeaponsButton = (Button) rootView.findViewById(R.id.plusEnemyWeaponsButton);
		plusEnemyShieldsButton = (Button) rootView.findViewById(R.id.plusEnemyShieldsButton);

		minusWeaponsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setCurrentShipWeapons(Math.max(adv.getCurrentShipWeapons() - 1, 0));
				refreshScreensFromResume();
			}
		});

		minusShieldsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setCurrentShipShields(Math.max(adv.getCurrentShipShields() - 1, 0));
				refreshScreensFromResume();

			}
		});

		plusWeaponsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setCurrentShipWeapons(Math.min(adv.getCurrentShipWeapons() + 1, adv.getInitialShipWeapons()));
				refreshScreensFromResume();

			}
		});

		plusShieldsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setCurrentShipWeapons(Math.min(adv.getCurrentShipShields() + 1, adv.getInitialShipShields()));
				refreshScreensFromResume();

			}
		});

		minusEnemyWeaponsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemyWeapons--;
				refreshScreensFromResume();

			}
		});

		minusEnemyShieldsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemyShields--;
				refreshScreensFromResume();

			}
		});
		plusEnemyWeaponsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemyWeapons++;
				refreshScreensFromResume();

			}
		});
		plusEnemyShieldsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemyShields++;
				refreshScreensFromResume();

			}
		});

		attackButton = (Button) rootView.findViewById(R.id.buttonAttack);

		attackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (enemyShields == 0 || adv.getCurrentShipShields() == 0)
					return;
				
				combatResult.setText("");

				int me = DiceRoller.roll2D6();
				int enemy = DiceRoller.roll2D6();

				if (me < adv.getCurrentShipWeapons()) {
					enemyShields -= 2;
					if (enemyShields <= 0) {
						enemyShields = 0;
						adv.showAlert("Direct hit!. You've defeated your opponent!");
					} else {
						combatResult.setText("Direct hit!");
					}
				} else{
					combatResult.setText("You've missed...");
				} 
				
				if (enemy < enemyWeapons) {
					adv.setCurrentShipShields(adv.getCurrentShipShields()-2);
					if (adv.getCurrentShipShields() <= 0) {
						adv.setCurrentShipShields(0);
						adv.showAlert("The enemy has destroyed your ship...");
					} else {
						combatResult.setText(combatResult.getText().toString()+"\nThe enemy has hit your ship.");
					}
				} else{
					combatResult.setText(combatResult.getText().toString()+"\nThe enemy ship missed!");
				} 
				
				refreshScreensFromResume();
			}
		});

		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {
		
		STAdventure adv = (STAdventure) getActivity();
		
		enemyShieldsValue.setText("" + enemyShields);
		enemyWeaponsValue.setText("" + enemyWeapons);
		shipShieldsValue.setText("" + adv.getCurrentShipShields());
		shipWeaponsValue.setText("" + adv.getCurrentShipWeapons());
	}
}
