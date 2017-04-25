package pt.joaomneto.titancompanion.adventure.impl.fragments.bnc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.BNCAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment;

public class BNCAdventureVitalStatsFragment extends AdventureVitalStatsFragment {

	TextView willpowerValue = null;

	Button increaseWillpowerButton = null;

	Button decreaseWillpowerButton = null;

	public BNCAdventureVitalStatsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_25bnc_adventure_vitalstats, container, false);

		initialize(rootView);

		decreaseWillpowerButton = (Button) rootView
				.findViewById(R.id.minusWillpowerButton);
		increaseWillpowerButton = (Button) rootView
				.findViewById(R.id.plusWillpowerButton);
		willpowerValue = (TextView) rootView.findViewById(R.id.statsWillpowerValue);
		final BNCAdventure adv = (BNCAdventure) getActivity();

		decreaseWillpowerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getCurrentWillpower() > 0)
					adv.setCurrentWillpower(adv.getCurrentWillpower() - 1);
				refreshScreensFromResume();

			}
		});

		increaseWillpowerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getCurrentWillpower() < adv.getInitialWillpower())
					adv.setCurrentWillpower(adv.getCurrentWillpower() + 1);
				refreshScreensFromResume();

			}
		});

		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {
		super.refreshScreensFromResume();
		BNCAdventure adv = (BNCAdventure) getActivity();
		willpowerValue.setText("" + adv.getCurrentWillpower());

	}

}
