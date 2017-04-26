package pt.joaomneto.titancompanion.adventure.impl.fragments.aod;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.impl.AODAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.aod.adapter.SoldierListAdapter;
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.adapter.RobotListAdapter;

public class AODAdventureSoldiersFragment extends AdventureFragment {

	protected static Integer[] gridRows;

	protected ListView soldiersList = null;
	protected Button buttonStartSkirmish = null;
	protected View rootView = null;

	public AODAdventureSoldiersFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_36aod_adventure_soldiers, container, false);

		init();
		
/*
		registerForContextMenu(soldiersList);
*/

		return rootView;
	}

	protected void init() {

		final AODAdventure adv = (AODAdventure) this.getActivity();

		buttonStartSkirmish = (Button) rootView.findViewById(R.id.aod_soldiers_buttonStartSkirmish);
		soldiersList = (ListView) rootView.findViewById(R.id.aod_soldiers_soldiersList);
		soldiersList.setAdapter(new SoldierListAdapter(adv, adv.getSoldiers()));


		buttonStartSkirmish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				startBattle();
			}

		});

		refreshScreensFromResume();
	}



	protected void startBattle() {
	}

	@Override
	public void refreshScreensFromResume() {
		AODAdventure adv = (AODAdventure) this.getActivity();
		RobotListAdapter adapter = (RobotListAdapter) soldiersList.getAdapter();
		adapter.notifyDataSetChanged();

	}

}
