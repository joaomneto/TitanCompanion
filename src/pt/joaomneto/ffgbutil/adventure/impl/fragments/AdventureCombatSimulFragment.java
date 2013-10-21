package pt.joaomneto.ffgbutil.adventure.impl.fragments;

import java.util.ArrayList;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class AdventureCombatSimulFragment extends DialogFragment implements AdventureFragment {

	Button addEnemyButton = null;
	Button attackButton = null;
	Button testCombatLuckButton = null;
	Button escapeButton = null;

	GridView enemyGrid = null;

	TextView combatResult = null;

	final ArrayList<String> enemyList = new ArrayList<String>();

	boolean hit = false;
	boolean draw = false;
	boolean round = false;

	int i = 0;

	public AdventureCombatSimulFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_adventure_combat_simul, container, false);

		final Adventure adv = (Adventure) getActivity();
		combatResult = (TextView) rootView.findViewById(R.id.combatResult);

		attackButton = (Button) rootView.findViewById(R.id.buttonAttack);
		testCombatLuckButton = (Button) rootView
				.findViewById(R.id.buttonTestCombatLuck);
		escapeButton = (Button) rootView.findViewById(R.id.buttonEscape);
		addEnemyButton = (Button) rootView.findViewById(R.id.buttonAddEnemy);

		enemyGrid = (GridView) rootView.findViewById(R.id.enemyGrid);

		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(adv,
	            android.R.layout.simple_list_item_1, android.R.id.text1, enemyList);
		
		enemyGrid.setAdapter(adapter);

		attackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		testCombatLuckButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		escapeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				adv.setCurrentStamina(adv.getCurrentStamina() - 2);
				hit = false;
				draw = false;
				if (adv.getCurrentStamina() < 0) {
					adv.setCurrentStamina(0);
					adv.showAlert("You're dead...");
				} else if (adv.getCurrentStamina() == 0) {
					adv.setCurrentStamina(0);
					adv.showAlert("You've been dealt a fatal blow (try your luck!)");
				} else {
					combatResult
							.setText("You've escaped! You lose 2 stamina points");
				}

			}
		});

		addEnemyButton.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View arg0) {
				enemyList.add(""+(++i));
				((ArrayAdapter<String>)enemyGrid.getAdapter()).notifyDataSetChanged();
			}
		});

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {
		// TODO Auto-generated method stub
		
	}
}
