package pt.joaomneto.titancompanion.adventure.impl.fragments.pof;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.POFAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment;

public class POFAdventureVitalStatsFragment extends AdventureVitalStatsFragment {

	TextView powerValue = null;

	Button increasePowerButton = null;

	Button decreasePowerButton = null;

	public POFAdventureVitalStatsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_28pof_adventure_vitalstats, container, false);

		initialize(rootView);

		decreasePowerButton = (Button) rootView
				.findViewById(R.id.minusPowerButton);
		increasePowerButton = (Button) rootView
				.findViewById(R.id.plusPowerButton);
		powerValue = (TextView) rootView.findViewById(R.id.statsPowerValue);
		final POFAdventure adv = (POFAdventure) getActivity();

		decreasePowerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getCurrentPower() > 0)
					adv.setCurrentPower(adv.getCurrentPower() - 1);
				refreshScreensFromResume();

			}
		});

		increasePowerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getCurrentPower() < adv.getInitialPower())
					adv.setCurrentPower(adv.getCurrentPower() + 1);
				refreshScreensFromResume();

			}
		});

		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {
		super.refreshScreensFromResume();
		POFAdventure adv = (POFAdventure) getActivity();
		powerValue.setText("" + adv.getCurrentPower());

	}

}
