package pt.joaomneto.ffgbutil.adventure.impl.fragments.ff;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.FFAdventure;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FFVehicleCombatFragment extends DialogFragment implements AdventureFragment {

	TextView vehicleFirepowerValue = null;
	TextView vehicleArmourValue = null;
	TextView enemyFirepowerValue = null;
	TextView enemyArmourValue = null;

	Button attackButton = null;

	Button minusFirepowerButton = null;
	Button minusarmorButton = null;
	Button plusFirepowerButton = null;
	Button plusarmorButton = null;

	Button minusEnemyFirepowerButton = null;
	Button minusEnemyarmorButton = null;
	Button plusEnemyFirepowerButton = null;
	Button plusEnemyarmorButton = null;

	int enemyFirepower = 0;
	int enemyArmour = 0;
	TextView combatResult = null;

	public FFVehicleCombatFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_13ff_adventure_vehiclecombat, container, false);

		final FFAdventure adv = (FFAdventure) getActivity();

		vehicleFirepowerValue = (TextView) rootView.findViewById(R.id.vehiclefirepowerValue);
		vehicleArmourValue = (TextView) rootView.findViewById(R.id.vehiclearmorValue);
		enemyFirepowerValue = (TextView) rootView.findViewById(R.id.enemyfirepowerValue);
		enemyArmourValue = (TextView) rootView.findViewById(R.id.enemyarmorValue);

		vehicleFirepowerValue.setText(""+adv.getCurrentFirepower());
		vehicleArmourValue.setText(""+adv.getCurrentArmour());

		combatResult = (TextView) rootView.findViewById(R.id.combatResult);

		minusFirepowerButton = (Button) rootView.findViewById(R.id.minusfirepowerButton);
		minusarmorButton = (Button) rootView.findViewById(R.id.minusarmorButton);
		plusFirepowerButton = (Button) rootView.findViewById(R.id.plusfirepowerButton);
		plusarmorButton = (Button) rootView.findViewById(R.id.plusarmorButton);

		minusEnemyFirepowerButton = (Button) rootView.findViewById(R.id.minusEnemyfirepowerButton);
		minusEnemyarmorButton = (Button) rootView.findViewById(R.id.minusEnemyarmorButton);
		plusEnemyFirepowerButton = (Button) rootView.findViewById(R.id.plusEnemyfirepowerButton);
		plusEnemyarmorButton = (Button) rootView.findViewById(R.id.plusEnemyarmorButton);

		minusFirepowerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setCurrentFirepower(Math.max(adv.getCurrentFirepower() - 1, 0));
				refreshScreensFromResume();
			}
		});

		minusarmorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setCurrentArmour(Math.max(adv.getCurrentArmour() - 1, 0));
				refreshScreensFromResume();

			}
		});

		plusFirepowerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setCurrentFirepower(Math.min(adv.getCurrentFirepower() + 1, adv.getInitialFirepower()));
				refreshScreensFromResume();

			}
		});

		plusarmorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setCurrentFirepower(Math.min(adv.getCurrentArmour() + 1, adv.getInitialArmour()));
				refreshScreensFromResume();

			}
		});

		minusEnemyFirepowerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemyFirepower--;
				refreshScreensFromResume();

			}
		});

		minusEnemyarmorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemyArmour--;
				refreshScreensFromResume();

			}
		});
		plusEnemyFirepowerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemyFirepower++;
				refreshScreensFromResume();

			}
		});
		plusEnemyarmorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemyArmour++;
				refreshScreensFromResume();

			}
		});

		attackButton = (Button) rootView.findViewById(R.id.buttonAttack);

		attackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (enemyArmour == 0 || adv.getCurrentArmour() == 0)
					return;
				
				combatResult.setText("");

				int myAttack = DiceRoller.roll2D6()+adv.getCurrentFirepower();
				int enemyAttack = DiceRoller.roll2D6()+enemyFirepower;

				if (myAttack < enemyAttack) {
					int damage = DiceRoller.rollD6();
					enemyArmour -= damage;
					if (enemyArmour <= 0) {
						enemyArmour = 0;
						adv.showAlert("Direct hit!. You've defeated your opponent!");
					} else {
						combatResult.setText("Direct hit! (-"+damage+" Armour)");
					}
				}else if (enemyAttack < enemyFirepower) {
					int damage = DiceRoller.rollD6();
					adv.setCurrentArmour(adv.getCurrentArmour()-damage);
					if (adv.getCurrentArmour() <= 0) {
						adv.setCurrentArmour(0);
						adv.showAlert("The enemy has destroyed your vehicle...");
					} else {
						combatResult.setText("The enemy has hit your vehicle. (-"+damage+" Armour)");
					}
				} else{
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
		
		FFAdventure adv = (FFAdventure) getActivity();
		
		enemyArmourValue.setText("" + enemyArmour);
		enemyFirepowerValue.setText("" + enemyFirepower);
		vehicleArmourValue.setText("" + adv.getCurrentArmour());
		vehicleFirepowerValue.setText("" + adv.getCurrentFirepower());
	}
}
